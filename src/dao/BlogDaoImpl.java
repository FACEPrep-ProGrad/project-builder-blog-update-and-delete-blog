package dao;

import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;

public class BlogDaoImpl implements  BlogDaoInterface
{

	public void insertBlog(Blog blog) 
	{
	try {
		Connection con = ConnectionManager.getConnection();
		
		String sql = "INSERT INTO blog(blogId,blogName ,blogDescription,postedOn)VALUES(?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);;
		
		
			st.setLong(1,blog.getBlogId());
		
			st.setString(2,blog.getBlogTitle());
		
			st.setString(3,blog.getBlogDescription());
		
			st.setDate(4,java.sql.Date.valueOf(blog.getPostedOn()));
		
			st.executeUpdate();
		
			con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	
	}

	
	public List<Blog> selectAllBlogs()  
	{
		try {
		List<Blog> list=new ArrayList<Blog>();
	
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM blog");
		while(rs.next())
		{
			int id = rs.getInt(1);
			
			String name = rs.getString(2);
			
			String description = rs.getString(3);
			
			Date d = rs.getDate(4);
				
			Blog blog = new Blog();
			blog.setBlogId(id);
			blog.setBlogTitle(name);
		    blog.setBlogDescription(description);
		
			LocalDate postedOn = rs.getDate("postedOn").toLocalDate();
			
			blog.setPostedOn(postedOn);
			list.add(blog);
		}
		
		return list;
	} catch (Exception e) {
		System.out.println(e);
	}
		return null;
	}	
	
	
	public Blog selectBlog(int blogId) {
		try {
			Blog blog = null;
		
	     	Connection con = ConnectionManager.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT blogId,blogName,blogDescription,postedOn FROM blog WHERE blogId =?");
			st.setInt(1, blogId);
		
			ResultSet rs = st.executeQuery();

			
			while (rs.next()) {
				int Id = rs.getInt("blogId");
				String blogTitle = rs.getString("blogName");
				String blogDescription = rs.getString("blogDescription");
				LocalDate postedOn = rs.getDate("postedOn").toLocalDate();
				
				blog = new Blog();
				blog.setBlogId(Id);
				blog.setBlogTitle(blogTitle);
				blog.setBlogDescription(blogDescription);
				blog.setPostedOn(postedOn);
			}
		
		return blog;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	
	public boolean deleteBlog(int id) throws SQLException {
		try {
		
		boolean del;
		Connection con = ConnectionManager.getConnection();
		PreparedStatement st= con.prepareStatement("DELETE FROM blog WHERE blogId = ?");
		st.setInt(1, id);
		del = st.executeUpdate() > 0;
		
		return del;
		
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public boolean updateBlog(Blog blog) throws Exception {
		boolean up = false;

		try {

			Connection con = ConnectionManager.getConnection();
			
			PreparedStatement st = con.prepareStatement("UPDATE blog SET  blogName = ?, blogDescription = ?, postedOn = ? WHERE blogId = ?"); 
			
			st.setString(1, blog.getBlogTitle());
			st.setString(2,blog.getBlogDescription());
			st.setDate(3, java.sql.Date.valueOf(blog.getPostedOn()));
			st.setInt(4, blog.getBlogId());
			up = st.executeUpdate() > 0;
			
		}catch (Exception e) {
			System.out.println(e);
		}
		return up;
	}
}