//
//  AlixPayDemoViewController.m
//  AlixPayDemo
//
//  Created by Jing Wen on 7/27/11.
//  Copyright 2011 alipay.com. All rights reserved.
//

#import "AlixPayDemoViewController.h"
#import "AlixPayOrder.h"
#import "AlixPayResult.h"
#import "AlixPay.h"
#import "DataSigner.h"

@implementation Product
@synthesize price = _price;
@synthesize subject = _subject;
@synthesize body = _body;
@synthesize orderId = _orderId;

@end


@interface AlixPayDemoViewController(PrivateMethods)

- (void)generateData;

@end


@implementation AlixPayDemoViewController


/*
 *随机生成15位订单号,外部商户根据自己情况生成订单号
 */
- (NSString *)generateTradeNO
{
	const int N = 15;
	
	NSString *sourceString = @"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	NSMutableString *result = [[[NSMutableString alloc] init] autorelease];
	srand(time(0));
	for (int i = 0; i < N; i++)
	{   
		unsigned index = rand() % [sourceString length];
		NSString *s = [sourceString substringWithRange:NSMakeRange(index, 1)];
		[result appendString:s];
	}
	return result;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation{
	if (IS_IPAD) {
		return (toInterfaceOrientation == UIInterfaceOrientationLandscapeRight
				|| toInterfaceOrientation == UIInterfaceOrientationLandscapeLeft);
	}	
	return NO;
}

/*
 *产生商品列表数据,这里的商品信息是写死的,可以修改,仅供测试
 */
- (void)generateData{
	NSArray *subjects = [[NSArray alloc] initWithObjects:@"话费充值",
						 @"魅力香水",@"珍珠项链",@"三星 原装移动硬盘",
						 @"发箍发带",@"台版N97I",@"苹果手机",
						 @"蝴蝶结",@"韩版雪纺",@"五皇纸箱",nil];
	NSArray *body = [[NSArray alloc] initWithObjects:@"[四钻信誉]北京移动30元 电脑全自动充值 1到10分钟内到账",
					 @"新年特惠 adidas 阿迪达斯走珠 香体止汗走珠 多种香型可选",
					 @"[2元包邮]韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15",
					 @"三星 原装移动硬盘 S2 320G 带加密 三星S2 韩国原装 全国联保",
					 @"[肉来来]超热卖 百变小领巾 兔耳朵布艺发箍发带",
					 @"台版N97I 有迷你版 双卡双待手机 挂QQ JAVA 炒股 来电归属地 同款比价",
					 @"山寨国产红苹果手机 Hiphone I9 JAVA QQ后台 飞信 炒股 UC",
					 @"[饰品实物拍摄]满30包邮 三层绸缎粉色 蝴蝶结公主发箍多色入",
					 @"饰品批发价 韩版雪纺纱圆点布花朵 山茶玫瑰花 发圈胸针两用 6002",
					 @"加固纸箱 会员包快递拍好去运费冲纸箱首个五皇",nil];
	
	if (nil == _products) {
		_products = [[NSMutableArray alloc] init];
	}
	else {
		[_products removeAllObjects];
	}

	for (int i = 0; i < [subjects count]; ++i) {
		Product *product = [[Product alloc] init];
		product.subject = [subjects objectAtIndex:i];
		product.body = [body objectAtIndex:i];
		product.price = 0.01f; 
		[_products addObject:product];
		[product release];
	}
	
	[subjects release], subjects = nil;
	[body release], body = nil;
}

- (void)dealloc {
    [navBar release];
	[productTv release];
	[_products release];
	[super dealloc];
}

- (void)viewDidLoad {
    [super viewDidLoad];
	[self generateData];
}

#pragma mark -
#pragma mark UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
	return 55.0f;
}

#pragma mark -
#pragma mark UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	return [_products count];
}

//
//用TableView呈现测试数据,外部商户不需要考虑
//
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
	UITableViewCell *cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault 
													reuseIdentifier:@"Cell"] autorelease];
		
	Product *product = [_products objectAtIndex:indexPath.row];
	UIView *adaptV = [[UIView alloc] initWithFrame:CGRectMake(10, 0, 
															  productTv.bounds.size.width-10, cell.bounds.size.height)];
	
	UILabel *subjectLb = [[UILabel alloc] initWithFrame:CGRectMake(0, 5, adaptV.bounds.size.width, 20)];
	subjectLb.text = product.subject;
	[subjectLb setFont:[UIFont boldSystemFontOfSize:14]];
	subjectLb.backgroundColor = [UIColor clearColor];
	[adaptV addSubview:subjectLb];
	[subjectLb release];
	
	UILabel *bodyLb = [[UILabel alloc] initWithFrame:CGRectMake(0, 25, 
																adaptV.bounds.size.width, 20)];
	bodyLb.text = product.body;
	[bodyLb setFont:[UIFont systemFontOfSize:12]];
	[adaptV addSubview:bodyLb];
	[bodyLb release];
	
	UILabel *priceLb = [[UILabel alloc] initWithFrame:CGRectMake(productTv.bounds.size.width-150, 5, 100, 20)];
	priceLb.text = [NSString stringWithFormat:@"一口价：%.2f",product.price];
	[priceLb setFont:[UIFont systemFontOfSize:12]];
	[adaptV addSubview:priceLb];
	[priceLb release];
	
	[cell.contentView addSubview:adaptV];
	[adaptV release];
	
	return cell;
}

//
//选中商品调用支付宝安全支付
//
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	/*
	 *点击获取prodcut实例并初始化订单信息
	 */
	Product *product = [_products objectAtIndex:indexPath.row];
	
	/*
	 *商户的唯一的parnter和seller。
	 *本demo将parnter和seller信息存于（AlixPayDemo-Info.plist）中,外部商户可以考虑存于服务端或本地其他地方。
	 *签约后，支付宝会为每个商户分配一个唯一的 parnter 和 seller。
	 */
	//如果partner和seller数据存于其他位置,请改写下面两行代码
	NSString *partner = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"Partner"];
    NSString *seller = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"Seller"];
	
	//partner和seller获取失败,提示
	if ([partner length] == 0 || [seller length] == 0)
	{
		UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示"
														message:@"缺少partner或者seller。" 
													   delegate:self 
											  cancelButtonTitle:@"确定" 
											  otherButtonTitles:nil];
		[alert show];
		[alert release];
		return;
	}
	
	/*
	 *生成订单信息及签名
	 *由于demo的局限性，本demo中的公私钥存放在AlixPayDemo-Info.plist中,外部商户可以存放在服务端或本地其他地方。
	 */
	//将商品信息赋予AlixPayOrder的成员变量
	AlixPayOrder *order = [[AlixPayOrder alloc] init];
	order.partner = partner;
	order.seller = seller;
	order.tradeNO = [self generateTradeNO]; //订单ID（由商家自行制定）
	order.productName = product.subject; //商品标题
	order.productDescription = product.body; //商品描述
	order.amount = [NSString stringWithFormat:@"%.2f",product.price]; //商品价格
	order.notifyURL =  @"http://www.xxx.com"; //回调URL
	
	//应用注册scheme,在AlixPayDemo-Info.plist定义URL types,用于安全支付成功后重新唤起商户应用
	NSString *appScheme = @"AlixPayDemo"; 
	
	//将商品信息拼接成字符串
	NSString *orderSpec = [order description];
	NSLog(@"orderSpec = %@",orderSpec);
	
	//获取私钥并将商户信息签名,外部商户可以根据情况存放私钥和签名,只需要遵循RSA签名规范,并将签名字符串base64编码和UrlEncode
	id<DataSigner> signer = CreateRSADataSigner([[NSBundle mainBundle] objectForInfoDictionaryKey:@"RSA private key"]);
	NSString *signedString = [signer signString:orderSpec];
	
	//将签名成功字符串格式化为订单字符串,请严格按照该格式
	NSString *orderString = nil;
	if (signedString != nil) {
		orderString = [NSString stringWithFormat:@"%@&sign=\"%@\"&sign_type=\"%@\"",
								 orderSpec, signedString, @"RSA"];
	}
	//获取安全支付单例并调用安全支付接口
	AlixPay * alixpay = [AlixPay shared];
	int ret = [alixpay pay:orderString applicationScheme:appScheme];
	
	if (ret == kSPErrorAlipayClientNotInstalled) {
		UIAlertView * alertView = [[UIAlertView alloc] initWithTitle:@"提示" 
															 message:@"您还没有安装支付宝的客户端，请先装。" 
															delegate:self 
												   cancelButtonTitle:@"确定" 
												   otherButtonTitles:nil];
		[alertView setTag:123];
		[alertView show];
		[alertView release];
	}
	else if (ret == kSPErrorSignError) {
		NSLog(@"签名错误！");
	}

	[tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if (alertView.tag == 123) {
		NSString * URLString = [NSString stringWithString:@"http://itunes.apple.com/cn/app/id333206289?mt=8"];
		[[UIApplication sharedApplication] openURL:[NSURL URLWithString:URLString]];
	}
}

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}

@end
