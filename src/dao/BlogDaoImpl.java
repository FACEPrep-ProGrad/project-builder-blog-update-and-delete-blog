package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;

public class BlogDaoImpl{
	private static final String SELECT_BLOGS_BY_ID = "select blogId,blogname,blogDescription from blog where blogId =?";
	private static final String DELETE_BLOGS_BY_ID = "delete from blog where blogId = ?";
	private static final String UPDATE_BLOGS = "update blog set  blogName = ?, blogDescription = ?  where blogId = ?";

	public void insertBlog(Blog blog) throws Exception
	{
		// TODO Auto-generated method stub
		Connection con=ConnectionManager.getConnection();
		String sql="INSERT INTO BLOGS(blogId,blogName,blogDescription) VALUES(?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, blog.getBlogId());
		st.setString(2,blog.getBlogTitle());
		st.setString(3,blog.getBlogDescription());
		st.executeUpdate();
		con.close();	
	}
	public Blog selectBlog(int blogId) throws Exception {
		Blog blog = null;
		System.out.println(blogId);
		Connection con = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(SELECT_BLOGS_BY_ID);
		preparedStatement.setInt(1, blogId);
		System.out.println(preparedStatement);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int Id = rs.getInt("blogId");
			String blogTitle = rs.getString("blogName");
			String blogDescription = rs.getString("blogDescription");				
			blog = new Blog();
			blog.setBlogId(Id);
			blog.setBlogTitle(blogTitle);
			blog.setBlogDescription(blogDescription);				
		}
		return blog;
	}
	public List<Blog> selectAllBlogs() throws Exception {
		Blog blog = null;
		List<Blog> blogList = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("select * from BLOGS");
		System.out.println(preparedStatement);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int Id = rs.getInt("blogId");
			String blogTitle = rs.getString("blogName");
			String blogDescription = rs.getString("blogDescription");				
			blog = new Blog();
			blog.setBlogId(Id);
			blog.setBlogTitle(blogTitle);
			blog.setBlogDescription(blogDescription);
			blogList.add(blog);
		}
		return blogList;
	}
	public boolean deleteBlog(int id) throws Exception {
		System.out.println(id);
		boolean rowDeleted;
		Connection con = ConnectionManager.getConnection();
		PreparedStatement statement = con.prepareStatement(DELETE_BLOGS_BY_ID);
		statement.setInt(1, id);
		rowDeleted = statement.executeUpdate()>0;
		return rowDeleted;
	}
	public boolean updateBlog(Blog blog) throws Exception {
		boolean rowUpdated = false;
		System.out.println(blog.getBlogId());
		System.out.println(blog.getBlogTitle());
		System.out.println(blog.getBlogDescription());
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_BLOGS); 
		statement.setInt(1, blog.getBlogId());
		statement.setString(2, blog.getBlogTitle());
		statement.setString(3,blog.getBlogDescription());
		rowUpdated = statement.executeUpdate() > 0;
		System.out.println(rowUpdated);
		return rowUpdated;
	}
}