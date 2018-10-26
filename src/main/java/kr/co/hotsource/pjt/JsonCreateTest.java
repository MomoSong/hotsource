package kr.co.hotsource.pjt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class JsonCreateTest {
	@Autowired
	private DBOpen dbopen;

	@Autowired
	private DBClose dbclose;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;

	public JsonCreateTest() {
		System.out.println("----JCTDAO() 按眉 积己");
	}
	public String getJsonString(String filename) {
		String jsonString = "";
		JSONObject jsonobject = null;
		JSONArray jsonArray = new JSONArray();
		System.out.println("getJsonString : " + filename);
		try {
			con = dbopen.getConnection();
			String sql = null;
			sql = " SELECT name as title, id as 'key' from "+filename+";";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				jsonobject = new JSONObject();

				for (int i = 0; i < metaData.getColumnCount(); i++) {
					jsonobject.put(metaData.getColumnLabel(i + 1), rs.getObject(i + 1));

				}

				jsonArray.add(jsonobject);
			}

			if (jsonArray.size() > 0) {

				jsonString = jsonArray.toString();
			}
			//System.out.println(jsonString);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt, rs);
		}

		return jsonString;
	}

	public ArrayList<TreeVO> list(String filename) {
		ArrayList<TreeVO> list = null;
		PjtDTO dto = null;
		try {
			con = dbopen.getConnection();
			String sql = null;
			sql = " SELECT name as title, id as 'key', parent_id as parent from "+filename+";";

			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<TreeVO>();
				do {
					TreeVO TV = new TreeVO();
					TV.setKey(rs.getString("key"));
					TV.setTitle(rs.getString("title"));
					TV.setParent(rs.getString("parent"));

					list.add(TV);
				} while (rs.next());
			}

		} catch (Exception e) {
			System.out.println("格废角菩 : " + e);
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return list;
	}// list() end
} // class end
