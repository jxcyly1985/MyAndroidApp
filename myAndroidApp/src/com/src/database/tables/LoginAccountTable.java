package com.src.database.tables;

public class LoginAccountTable {

	private static LoginAccountTable mSelf;

	private final String TableName = "LoginT";
	private final String UserStr = "LoginAccount";
	private final String UserPwd = "LoginPwd";
	private final String whiteSP = " ";
	private final String DotSP = ",";

	public static LoginAccountTable getInstance() {
		if (mSelf == null) {
			return mSelf = new LoginAccountTable();
		}
		return mSelf;
	}

	public String getTableName() {
		return TableName;
	}

	public String getCreateSQL() {
		// StringBuilder StringBuilder = new StringBuilder();
		// String CreateTable =
		// StringBuilder.append("Create table if not exists").append(whiteSP)
		// .append(TableName)
		// .append("(").append(UserStr).append(whiteSP).append("varchar(20)")
		// .append(DotSP).append(whiteSP)
		// .append(UserPwd).append(whiteSP).append("varchar(20)")
		// .append(");")
		// .toString();

		String CreateTable = "create table LoginT (LoginAccount varchar(20), LoginPwd varchar(20),  Number Integer)";

		return CreateTable;
	}

	public String getInsertSQL(String a, String p) {
		StringBuilder StringBuilder = new StringBuilder();
		String insertTable = StringBuilder.append("insert info")
				.append(whiteSP).append(TableName).append("(").append(UserStr)
				.append(whiteSP).append("varchar(20)").append(DotSP)
				.append(whiteSP).append(UserPwd).append(whiteSP)
				.append("varchar(20)").append(");").toString();

		return insertTable;
	}

	public String getUpdateSQL(String account, String newPwd) {
		StringBuilder StringBuilder = new StringBuilder();
		String updateTable = StringBuilder.append("update").append(whiteSP)
				.append(TableName).append("set").append(whiteSP)
				.append(UserPwd).append("=").append("'").append(newPwd)
				.append("'").append(whiteSP).append("where").append(whiteSP)
				.append(UserStr).append("=").append("'").append(account)
				.append("'").append(";").toString();

		return updateTable;
	}
}
