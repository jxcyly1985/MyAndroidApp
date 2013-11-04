package com.src.android.global;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.os.Handler;
import android.os.Message;

/**
 * 消息管理模块
 * 负责进行消息的分发处理
 * 维护所有的被观察者 
 * 以及处理给被观察者添加观察者
 * @author leiyong
 *
 */
public class MessageManager{
	
	private static MessageManager self;
	
	private MainHandler mainHandler = new MainHandler();
	protected MessageManager(){
		mapObervable = new HashMap<Integer, MessageObservable>();
	}
	
	public static MessageManager getInstance(){
		if(self == null){
				self = new MessageManager();
		}
		return self;
	}
	
	
	private HashMap<Integer, MessageObservable>		mapObervable;

	/** 添加对应消息的观察者 */
	public void addOberver(Integer what, Observer observer){
		MessageObservable observable;
		if(mapObervable.containsKey(what)){
			observable = mapObervable.get(what);
		}else{
			observable = new MessageObservable();
		}
		observable.addObserver(observer);
		mapObervable.put(what, observable);
	}
	
	/** 清除所有观察者 */
	public void deleteAllOberver(){
	
		Collection<MessageObservable> values = mapObervable.values();
		for(MessageObservable observable : values){
			observable.deleteObservers();
		}
	}
	
	/** 删除对应消息的观察者 */
	public void deleteOberver(Integer what, Observer observer){
		if(mapObervable.containsKey(what)){
			Observable observable = mapObervable.get(what);
			observable.deleteObserver(observer);
		}
	}
	
	/** 删除对应消息的观察者 */
	public void deleteOberver(Observer observer){
		for(Map.Entry<Integer, MessageObservable> entry : mapObervable.entrySet()){
			MessageObservable observable = entry.getValue();
			observable.deleteObserver(observer);
		}
	}
	
	public void notify(Message msg){
		int what = msg.what;
		Set<Integer> keyset = mapObervable.keySet();
		for(Integer i : keyset){
			if(i == what){
				mapObervable.get(what).notifyObservers(msg);
				break;
			}
		}
	}

	public void sendNotifyMessage(Message msg){
		mainHandler.sendMessage(msg);
	}
	
	private class MessageObservable extends Observable{
		
		public void notifyObservers(){
			setChanged();
			super.notifyObservers();
		}
		
		public void notifyObservers(Object data){
			setChanged();
			super.notifyObservers(data);
		}
	}
	
	public class MainHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg){
			MessageManager.this.notify(msg);
		}
		
	}
	

}
