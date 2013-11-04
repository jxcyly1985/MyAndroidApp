package com.src.android.activity;

import com.src.R;
import com.src.database.DBConfig;
import com.src.database.DBHelper;
import com.src.database.tables.LoginAccountTable;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DatabaseActivity extends Activity implements View.OnClickListener {

	private DBHelper mdbHelper;
	private SQLiteDatabase SqlDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_sample_layout);
		initView();
		initData();
	}

	public void initView() {
		findViewById(R.id.id_create_database_btn).setOnClickListener(this);
		findViewById(R.id.id_create_table_btn).setOnClickListener(this);
		findViewById(R.id.id_insert_data_btn).setOnClickListener(this);
		findViewById(R.id.id_insert_data_use_transaction_btn)
				.setOnClickListener(this);
		findViewById(R.id.id_query_data_btn).setOnClickListener(this);
		findViewById(R.id.id_update_data_btn).setOnClickListener(this);
		findViewById(R.id.id_delete_data_btn).setOnClickListener(this);
		findViewById(R.id.id_drop_table_btn).setOnClickListener(this);
		findViewById(R.id.id_delete_database_btn).setOnClickListener(this);

	}

	public void initData() {
		mdbHelper = new DBHelper(this, DBConfig.DBName, null,
				DBConfig.DBVersion);
		SqlDB = mdbHelper.getWritableDatabase();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.id_create_database_btn:
			Toast.makeText(this, "创建数据库", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_create_table_btn: {
			if (!DBConfig.tableIsExist(SqlDB, LoginAccountTable.getInstance()
					.getTableName())) {
				SqlDB.execSQL(LoginAccountTable.getInstance().getCreateSQL());
			}
			Toast.makeText(this, "创建数据库表格", Toast.LENGTH_SHORT).show();
		}
			break;
		case R.id.id_insert_data_btn: {

			ContentValues values = new ContentValues();
			values.put("LoginAccount", "15920523505");
			//values.put("LoginPwd", "jxcyly1985");
			SqlDB.insert(LoginAccountTable.getInstance().getTableName(),
					"LoginAccount", values);
			Toast.makeText(this, "插入数据", Toast.LENGTH_SHORT).show();
		}
			break;
		case R.id.id_insert_data_use_transaction_btn: {
			insertData();
			Toast.makeText(this, "事务插入数据", Toast.LENGTH_SHORT).show();
		}
			break;
		case R.id.id_query_data_btn: {
			queryData();
			Toast.makeText(this, "查询数据", Toast.LENGTH_SHORT).show();
		}
			break;
		case R.id.id_update_data_btn:
			SqlDB.execSQL(LoginAccountTable.getInstance().getUpdateSQL(
					"15920523505", "jxcyly1985"));
			Toast.makeText(this, "更新数据", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_delete_data_btn:
			Toast.makeText(this, "删除数据", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_drop_table_btn:
			Toast.makeText(this, "删除数据库表格", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_delete_database_btn:
			Toast.makeText(this, "删除数据库", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	public void testMultiThreadInsert() {

	}

	public void insertData() {

		for (int i = 0; i < 20; ++i) {
			Thread thread = new Thread(new InsertRunnable());
			thread.start();
		}
	}

	private void queryData() {
		String[] columns = new String[] { "LoginAccount", "LoginPwd", "Number" };
		Cursor cursor = SqlDB.query(LoginAccountTable.getInstance()
				.getTableName(), columns, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			boolean opbl = cursor.moveToFirst();
			String accout = cursor.getString(cursor
					.getColumnIndex("LoginAccount"));
			String pwd = cursor.getString(cursor
					.getColumnIndex("LoginPwd"));
			
			int number = cursor.getInt(cursor
					.getColumnIndex("Number"));
			
			System.out.println(accout);
			System.out.println(pwd);
			System.out.println(number);
			
			while (cursor.moveToNext()) {
				accout = cursor.getString(cursor
						.getColumnIndex("LoginAccount"));
				pwd = cursor.getString(cursor
						.getColumnIndex("LoginPwd"));
				
				System.out.println(accout);
				System.out.println(pwd);
			}
		}
	}

	public class InsertRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("leiyong", "" + SqlDB.isOpen());

			// 需要创建新的SQLiteOpenHelper对象获取getWritableDatabase 才是新的sqldatabase
			// 否则采用成员变量SQLiteOpenHelper获取的可能是同一个sqldatabase
			// 因此sqldatabase 需要同步 防止打开和关闭状态不同步
			 DBHelper mdbHelper = new DBHelper(DatabaseActivity.this,
			 DBConfig.DBName, null, DBConfig.DBVersion);
			SQLiteDatabase sqldatabase = mdbHelper.getWritableDatabase();
			Log.i("leiyong", "" + sqldatabase.isOpen());
			Log.i("leiyong", "" + sqldatabase.hashCode());
			// SQLiteDatabase sqldatabase = null;
			try {
				// sqldatabase = SqlDB;
				// sqldatabase.beginTransaction();

				ContentValues values = new ContentValues();
				values.put("LoginAccount", "15920523505");
				values.put("LoginPwd", "jxcyly1985");
				sqldatabase.insert(LoginAccountTable.getInstance()
						.getTableName(), "LoginAccount", values);
				// sqldatabase.insert(LoginAccountTable.getInstance().getTableName(),
				// "LoginAccount", values);
				// sqldatabase.insert(LoginAccountTable.getInstance().getTableName(),
				// "LoginAccount", values);
				// sqldatabase.insert(LoginAccountTable.getInstance().getTableName(),
				// "LoginAccount", values);

				// sqldatabase.setTransactionSuccessful();

			} finally {

				// sqldatabase.endTransaction();
				Log.i("leiyong", "endTransaction");
				// 关闭数据库
				sqldatabase.close();
			}
		}

	}
}
