package org.utils.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.utils.entity.Student;
import org.utils.util.JDBCUtils;

@Repository("JdbcDao")
public class JDBCDao implements Dao {

	// http://www.baeldung.com/properties-with-spring
	// Autowired annotation is not supported on static fields
	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	private static final Logger logger = Logger
			.getLogger(JDBCDao.class.getName());
	private static final Connection conn = JDBCUtils.getConnection();

	@Override
	public int addStudent(Student student) {
		int result = 0;
		String sql = "INSERT INTO student(name,course) VALUES (?,?)";
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setString(1, student.getName());
			pre.setString(2, student.getCourse());
			result = pre.executeUpdate();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		return result;
	}

	@Override
	public List<?> findAllStudent() {
		logger.info("datasourceUrl = " + datasourceUrl);
		List<?> students = null;
		String sql = "SELECT * FROM student";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			students = JDBCUtils.TranverseToList(resultSet, Student.class);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		return students;
	}

	@Override
	public int updateStudent(Student student) {
		int result = 0;
		String sql = "UPDATE student SET name = ?,course = ? WHERE id = ?";
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setString(1, student.getName());
			pre.setString(2, student.getCourse());
			pre.setLong(3, student.getId());
			result = pre.executeUpdate();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		return result;
	}

	@Override
	public int delStudentById(long id) {
		int result = 0;
		String sql = "DELETE FROM student WHERE id = ?";
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setLong(1, id);
			result = pre.executeUpdate();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
