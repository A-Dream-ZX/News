package com.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.news.entity.Comment;
import com.news.utils.JDBCUtil;

/**
 * CommentDao类负责对评论数据进行数据库操作
 */
public class CommentDao {
    // JDBC工具类实例，用于数据库连接和资源关闭
    private final JDBCUtil jdbcUtil = new JDBCUtil();

    /**
     * 根据新闻ID获取评论列表
     *
     * @param cnid 新闻ID
     * @return 评论列表
     */
    public List<Comment> findCommentByNews(int cnid) {
        String sql = "select * from comments where cnid=?";
        Connection con = jdbcUtil.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Comment> list = new ArrayList<Comment>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, cnid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Comment com = new Comment();
                com.setCid(rs.getInt("cid"));
                com.setCnid(rs.getInt("cnid"));
                com.setCcontent(rs.getString("ccontent"));
                com.setCdate(rs.getString("cdate"));
                com.setCip(rs.getString("cip"));
                com.setCauthor(rs.getString("cauthor"));
                list.add(com);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.closeDB(con, stmt, rs);
        }
        return list;
    }

    /**
     * 添加评论
     *
     * @param comment 要添加的评论对象
     * @return 影响行数，表示添加成功与否
     */
    public int addComment(Comment comment) {
        String sql = "insert into comments (cnid,ccontent,cdate,cip,cauthor) values (?,?,now(),?,?)";
        Connection con = jdbcUtil.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int n = 0;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, comment.getCnid());
            stmt.setString(2, comment.getCcontent());
            stmt.setString(3, comment.getCip());
            stmt.setString(4, comment.getCauthor());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.closeDB(con, stmt, rs);
        }
        return n;
    }

    /**
     * 删除评论
     *
     * @param cid 要删除的评论的ID
     * @return 影响行数，表示删除成功与否
     */
    public int deleteComment(int cid) {
        String sql = "delete from comments where cid=?";
        Connection con = jdbcUtil.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int n = 0;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, cid);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.closeDB(con, stmt, rs);
        }
        return n;
    }
}
