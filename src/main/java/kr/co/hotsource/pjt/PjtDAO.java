package kr.co.hotsource.pjt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class PjtDAO {
	@Autowired
	private DBOpen dbopen;

	@Autowired
	private DBClose dbclose;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<PjtDTO> list = null;

	public PjtDAO() {
		System.out.println("----PjtDAO() 객체 생성");
	}

	// 비지니스 로직 작성
	public int insert(PjtDTO dto) {
		System.out.println("DAO : " + dto.getPname());

		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(
					" insert into Pjt(Prono, Pname, Ptitle, Pexplain, Pcourse, Pversion, Filesize, Id, Planguage, date) ");
			sql.append(" values((SELECT IFNULL(MAX(Prono),0)+1 FROM Pjt as pjtno),?,?,?,?,?,?,?,?,now())");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getPname());
			pstmt.setString(2, dto.getPtitle());
			pstmt.setString(3, dto.getPexplain());
			pstmt.setString(4, dto.getPcourse());
			pstmt.setString(5, dto.getPversion());
			pstmt.setLong(6, dto.getFilesize());
			pstmt.setString(7, dto.getId());
			pstmt.setString(8, dto.getPlanguage());
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("추가실패:" + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// create() end

	public List list(int start, int end, String strSearchtype, String strSearch) throws Exception {
		List list = null;
		sql = new StringBuffer();
	    try {
	      con=dbopen.getConnection();
	      
	      sql.append(" SELECT a.* ");
	      sql.append(" FROM ( ");
	      sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
	      sql.append("       FROM ( ");
	      sql.append("             SELECT Prono,Ptitle,Pname,Pexplain,Pcourse,Pversion,Filesize,Id,Good,Date,Planguage ");
	      sql.append("             FROM Pjt, (SELECT @rownum:=0)R ");
	      sql.append("            ) b ");
	      sql.append("       ) a ");
	      sql.append(" WHERE a.RNUM >=? AND a.RNUM<=? ");
	      if("1".equals(strSearchtype)) { //자바에서 String은 부등호( == , != 같은)를 사용하지 않고 .equals를 사용한다.
	    	  sql.append(" AND a.Ptitle like concat('%', ?, '%')");
	      }
	      else if("2".equals(strSearchtype)) {
	    	  sql.append(" AND a.Pname like concat('%', ?, '%')");
	      }
	      else if("3".equals(strSearchtype)) {
	    	  sql.append(" AND a.Ptitle like concat('%', ?, '%') OR a.Pname like concat('%', ?, '%')");
	      }
	      sql.append(" GROUP BY a.Prono ");
	      sql.append(" ORDER BY RNUM DESC ");
	      
	      pstmt=con.prepareStatement(sql.toString());
	      pstmt.setInt(1, start);
          pstmt.setInt(2, end);
          if("1".equals(strSearchtype) || "2".equals(strSearchtype)) {
        	  pstmt.setString(3, strSearch);
          }
          else if("3".equals(strSearchtype)) {
        	  pstmt.setString(3, strSearch);
        	  pstmt.setString(4, strSearch);
          }
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = new ArrayList(end);
				do {
					PjtDTO dto = new PjtDTO();
					dto.setProno(rs.getInt("Prono"));
					dto.setPtitle(rs.getString("Ptitle"));
					dto.setPname(rs.getString("Pname"));
					dto.setPexplain(rs.getString("Pexplain"));
					dto.setPcourse(rs.getString("Pcourse"));
					dto.setPversion(rs.getString("Pversion"));
					dto.setFilesize(rs.getLong("Filesize"));
					dto.setId(rs.getString("Id"));
					dto.setGood(rs.getInt("Good"));
					dto.setDate(rs.getString("Date"));
					dto.setPlanguage(rs.getString("Planguage"));

					list.add(dto);
				} while (rs.next());
			}

		} catch (Exception e) {
			System.out.println("목록실패 : " + e);
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return list;
	}// list() end
	
	//글갯수 구하기
	  public int getArticleCount() throws Exception{
	      int res=0;
	      try{
	          con=dbopen.getConnection();
	          pstmt=con.prepareStatement("SELECT count(*) FROM Pjt ");
	          rs=pstmt.executeQuery();
	          if(rs.next()){
	              res=rs.getInt(1);             
	          }
	      }catch(Exception e){
	          e.printStackTrace();
	      }finally{
	          if(rs!=null) try { rs.close(); } catch(SQLException e){}
	          if(pstmt!=null) try { pstmt.close(); } catch(SQLException e){}
	          if(con!=null) try { con.close(); } catch(SQLException e){}
	      }
	      
	      return res;
	      
	  }//getArticleCount() end

	public PjtDTO read(int prono) {
		PjtDTO dto = null;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT *");
			sql.append(" FROM Pjt ");
			sql.append(" WHERE prono = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prono);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new PjtDTO();
				dto.setProno(rs.getInt("Prono"));
				dto.setPtitle(rs.getString("Ptitle"));
				dto.setPname(rs.getString("Pname"));
				dto.setPexplain(rs.getString("Pexplain"));
				dto.setPcourse(rs.getString("Pcourse"));
				dto.setPversion(rs.getString("Pversion"));
				dto.setFilesize(rs.getLong("Filesize"));
				dto.setId(rs.getString("Id"));
				dto.setGood(rs.getInt("Good"));
				dto.setPjtcnt(rs.getInt("pjtcnt"));
				dto.setDate(rs.getString("Date"));
				dto.setPlanguage(rs.getString("Planguage"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return dto;
	}// read() end

	public int readcntup(int prono) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE pjt ");
			sql.append(" SET pjtcnt = pjtcnt+1 ");
			sql.append(" WHERE prono=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prono);

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("조회수 증가 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// readcntup() end

	public int goodup(int prono) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE pjt ");
			sql.append(" SET good = good+1 ");
			sql.append(" WHERE prono=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prono);

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("추천수 증가 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// readcntup() end

	public int closerTB(String[] en_1, String filenameold) {
		String[] entry = en_1;

		int res = 0;
		try {
			con = dbopen.getConnection();
			String sql;
			// 테이블생성
			sql = "CREATE TABLE IF NOT EXISTS myjava." + entry[0] + " (\n" + "    id INT primary key not null,\n"
					+ "    name varchar(100) not null,\n" + "    parent_id INT\n" + ");";
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeUpdate();
			if (entry.length == 1) {
				System.out.println("길이가 1 일때 : ");
				// ㄱㄱ
				sql = "insert into " + entry[0] + "(id,name,parent_id)\n" + "values((SELECT IFNULL(MAX(id),0)+1 FROM "
						+ entry[0] + " as ClosTid),'" + entry[0] + "',null);";
				pstmt = con.prepareStatement(sql);
				res = pstmt.executeUpdate();
				con.close();
				pstmt.close();
				return res;
			} else if (entry.length == 2) {
				System.out.println("길이가 2 일때 : ");
				sql = "insert into " + entry[0] + "\n" + "values((SELECT IFNULL(MAX(id),0)+1 FROM " + entry[0]
						+ " as ClosTid),'" + entry[1] + "',1);";
				pstmt = con.prepareStatement(sql);
				res = pstmt.executeUpdate();
				con.close();
				pstmt.close();
				return res;
			} else {
				System.out.println("길이가 3이상일 때 일때 : select");
				sql = "select * from " + entry[0] + "\n" + "where name='" + entry[entry.length - 2] + "';";
				System.out.println(sql);
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					entry[entry.length - 2] = Integer.toString(rs.getInt("id"));
				}
				System.out.println("길이가 3이상일 때 일때 : insert");
				sql = "insert into " + entry[0] + " \n" + " values((SELECT IFNULL(MAX(id),0)+1 FROM " + entry[0]
						+ " as ClosTid),'" + entry[entry.length - 1] + "','" + entry[entry.length - 2] + "');";
				pstmt = con.prepareStatement(sql);

				res = pstmt.executeUpdate();
				con.close();
				pstmt.close();
			}
		} catch (Exception e) {
			System.out.println("추가실패:" + e);
			dbclose.close(con, pstmt);
		}
		return res;
	}// CloserTB() end

	public String getJsonString() {
		String jsonString = "";
		JSONObject jsonobject = null;
		JSONArray jsonArray = new JSONArray();

		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT id as 'key', name as title, parent_id as pid from CloT");
			pstmt = con.prepareStatement(sql.toString());
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
			System.out.println(jsonString);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt, rs);
		}

		return jsonString;
	}

	public int update(PjtDTO dto) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE Pjt ");
			sql.append(" SET Ptitle=?, Pexplain=?, Pname=?, Filesize=? ");
			sql.append(" WHERE prono=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getPtitle());
			pstmt.setString(2, dto.getPexplain());
			pstmt.setString(3, dto.getPname());
			pstmt.setLong(4, dto.getFilesize());
			pstmt.setInt(5, dto.getProno());

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("update");
			System.out.println("수정 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// update() end

	public int droptable(String panme) {
		int cnt = 0;
		try {
			String sql = "";
			con = dbopen.getConnection();
			sql = "drop table " + panme + ";";
			pstmt = con.prepareStatement(sql);
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt);
		}
		return cnt;
	}

	public int delete(int prono) {
		int cnt = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" DELETE FROM Pjt ");
			sql.append(" WHERE Prono=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prono);
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt);
		}
		return cnt;
	}// delete() end

	public ArrayList<PjtDTO> releaselist(String pname) {
		ArrayList<PjtDTO> list = null;
		PjtDTO dto = null;
		String releasename = "%" + pname + "%";
		try {
			con = dbopen.getConnection();

			sql = new StringBuffer();
			sql.append(" SELECT *");
			sql.append(" FROM Pjt");
			sql.append(" where pname like ?");
			sql.append(" ORDER BY pversion");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, releasename);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<PjtDTO>();
				do {
					dto = new PjtDTO();
					dto.setProno(rs.getInt("Prono"));
					dto.setPtitle(rs.getString("Ptitle"));
					dto.setPname(rs.getString("Pname"));
					dto.setPexplain(rs.getString("Pexplain"));
					dto.setPcourse(rs.getString("Pcourse"));
					dto.setPversion(rs.getString("Pversion"));
					dto.setFilesize(rs.getLong("Filesize"));
					dto.setId(rs.getString("Id"));
					dto.setGood(rs.getInt("Good"));
					dto.setDate(rs.getString("Date"));
					dto.setPlanguage(rs.getString("Planguage"));
					
					
					list.add(dto);
				} while (rs.next());
			}

		} catch (Exception e) {
			System.out.println("목록실패 : " + e);
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return list;
	}// list() end

	public int downcnt(int prono) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" select * from exam");
			sql.append(" where prono = ?  and downdate = CURDATE()");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prono);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				con = dbopen.getConnection();
				sql = new StringBuffer();
				sql.append(" UPDATE exam ");
				sql.append(" SET downcnt = downcnt+1 ");
				sql.append(" WHERE prono=? and downdate = CURDATE() ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, prono);

				res = pstmt.executeUpdate();
			} else {
				String sql;
				sql= "insert into exam (examno, prono, downdate,downcnt) values((SELECT IFNULL(MAX(examno),0)+1 FROM exam as EX)," + prono + ",CURDATE(),1);";
				pstmt = con.prepareStatement(sql);

				res = pstmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("다운로드수 증가 실패:" + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// create() end
	
	// 인기프로젝트 bestproject
			public List bestproject() throws Exception {
				List list = null;
				sql = new StringBuffer();
				try {
					con = dbopen.getConnection();
					sql.append(" SELECT Prono, Ptitle, Pname, Id, Date, Good ");
					sql.append(" FROM Pjt ");
					sql.append(" ORDER BY Good DESC limit 5 ");

					pstmt = con.prepareStatement(sql.toString());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						list = new ArrayList();
						do {
							PjtDTO dto = new PjtDTO();
							dto.setProno(rs.getInt("Prono"));
							dto.setPtitle(rs.getString("Ptitle"));
							dto.setPname(rs.getString("Pname"));
							dto.setId(rs.getString("Id"));
							dto.setDate(rs.getString("Date"));
							dto.setGood(rs.getInt("Good"));
							list.add(dto);
						} while (rs.next());
					}

				} catch (Exception e) {
					System.out.println("목록실패 : " + e);
				} finally {
					dbclose.close(con, pstmt, rs);
				}
				return list;
			}// bestproject() end
} // class end
