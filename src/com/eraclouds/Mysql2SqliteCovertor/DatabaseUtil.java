package com.eraclouds.Mysql2SqliteCovertor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 连接和使用数据库资源的工具类
 * 
 * @author yifangyou
 * @version gtm 2010-09-27
 */
public class DatabaseUtil {


	/**
	 * 获取执行SQL的工具
	 * 
	 * @param conn
	 *            数据库连接
	 * @return stmt
	 */
	public static int getFoundRows(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getStatement(conn);
			rs = stmt.executeQuery("SELECT FOUND_ROWS()");
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
			closeResultSet(rs);
		}
		return 0;
	}

	/**
	 * 获取执行SQL的工具
	 * 
	 * @param conn
	 *            数据库连接
	 * @return stmt
	 */
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return stmt;
	}

	/**
	 * 获取执行SQL的工具
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sql
	 *            SQL语句
	 * @return prepStmt
	 */
	public static PreparedStatement getPrepStatement(Connection conn, String sql) {
		PreparedStatement prepStmt = null;
		try {
			prepStmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prepStmt;
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param stmt
	 */
	public static void closeStatement(Statement stmt) {
		if (null != stmt) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param prepStmt
	 */
	public static void closePrepStatement(PreparedStatement prepStmt) {
		if (null != prepStmt) {
			try {
				prepStmt.close();
				prepStmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取结果集
	 * 
	 * @param stmt
	 *            执行SQL的工具
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public static ResultSet getResultSet(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	/**
	 * 关闭数据库资源
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Boolean setAutoCommit(Connection conn, boolean commitStatus) {
		if (conn == null) {
			return true;
		}
		try {
			boolean commit = conn.getAutoCommit();
			conn.setAutoCommit(commitStatus);
			return commit;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return true;
		}
	}

	public static boolean rollback(Connection conn, boolean oldCommitStatus) {
		if (conn == null) {
			return true;
		}
		try {
			conn.rollback(); // 事物回滚
			conn.setAutoCommit(oldCommitStatus);
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public static boolean commit(Connection conn, boolean oldCommitStatus) {
		if (conn == null) {
			return true;
		}
		try {
			conn.commit(); // 事物回滚
			conn.setAutoCommit(oldCommitStatus);
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public static int getLastId(PreparedStatement ps) {
		ResultSet rs = null;
		try {
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			closeResultSet(rs);
		}
		return -1;
	}

	

}
