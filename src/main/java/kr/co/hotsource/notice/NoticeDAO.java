package kr.co.hotsource.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.hotsource.board.boardDTO;
import kr.co.hotsource.notice.NoticeDTO;
import kr.co.hotsource.notice.ReplyDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class NoticeDAO {
	@Autowired
	private DBOpen dbopen;

	@Autowired
	private DBClose dbclose;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<NoticeDTO> list = null;
	ArrayList<ReplyDTO> reply = null;

	public NoticeDAO() {
		System.out.println("----NoticeDAO() 객체 생성");
	}

	// 비지니스 로직 작성

	public int create(NoticeDTO dto) {

		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" INSERT INTO notice(noticeno, code, title, content, filename, filesize) ");
			sql.append(" VALUES ((SELECT IFNULL(MAX(noticeno),0)+1 FROM notice as NB),'N', ?, ?, ?, ?) ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getFilename());
			pstmt.setLong(4, dto.getFilesize());

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
			con = dbopen.getConnection();

			sql.append(" SELECT a.* ");
			sql.append(" FROM ( ");
			sql.append("       SELECT @rownum:=@rownum+1 as RNUM, b.* ");
			sql.append("       FROM ( ");
			sql.append("             SELECT noticeno,title,date,readcnt,replycnt ");
			sql.append("             FROM notice, (SELECT @rownum:=0)R ");
			sql.append("             WHERE code='N' ORDER BY noticeno ASC ");
			sql.append("            ) b ");
			sql.append("       ) a ");
			sql.append(" WHERE a.RNUM >=? AND a.RNUM<=? ");
			 if("1".equals(strSearchtype)) { //자바에서 String은 부등호( == , != 같은)를 사용하지 않고 .equals를 사용한다.
		    	  sql.append(" AND a.title like concat('%', ?, '%')");
		      }
		      else if("2".equals(strSearchtype)) {
		    	  sql.append(" AND a.content like concat('%', ?, '%')");
		      }
		      else if("3".equals(strSearchtype)) {
		    	  sql.append(" AND a.title like concat('%', ?, '%') OR a.content like concat('%', ?, '%')");
		      }
			sql.append(" GROUP BY a.noticeno ");
			sql.append(" ORDER BY RNUM DESC ");

			pstmt = con.prepareStatement(sql.toString());
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
					NoticeDTO dto = new NoticeDTO();
					dto.setRnum(rs.getInt("rnum"));
					dto.setNoticeno(rs.getInt("noticeno"));
					dto.setTitle(rs.getString("title"));
					dto.setDate(rs.getString("date"));
					dto.setReadcnt(rs.getInt("readcnt"));
					dto.setReplycnt(rs.getInt("replycnt"));

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

	// 글갯수 구하기
	public int getArticleCount() throws Exception {
		int res = 0;
		try {
			con = dbopen.getConnection();
			pstmt = con.prepareStatement("SELECT count(*) FROM notice WHERE code='N'");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
				}
		}

		return res;

	}// getArticleCount() end

	public NoticeDTO read(int noticeno) {
		NoticeDTO dto = null;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT noticeno, title, content, readcnt, filename, filesize ");
			sql.append(" FROM notice ");
			sql.append(" WHERE noticeno = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new NoticeDTO();
				dto.setNoticeno(rs.getInt("noticeno"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setFilename(rs.getString("filename"));
				dto.setFilesize(rs.getLong("filesize"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return dto;
	}// read() end

	public int readcntup(int noticeno) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE notice ");
			sql.append(" SET readcnt = readcnt+1 ");
			sql.append(" WHERE noticeno=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("조회수 증가 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// readcntup() end

	public int delete(int noticeno) {
		int cnt = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" DELETE FROM notice ");
			sql.append(" WHERE noticeno=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt);
		}
		return cnt;
	}// delete() end

	public int update(NoticeDTO dto) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE notice ");
			sql.append(" SET title=?, content=?, filename=?, filesize=? ");
			sql.append(" WHERE noticeno=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getFilename());
			pstmt.setLong(4, dto.getFilesize());
			pstmt.setInt(5, dto.getNoticeno());

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("수정 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// update() end

	public ArrayList<ReplyDTO> reply(int noticeno) {
		ArrayList<ReplyDTO> reply = null;
		ReplyDTO dto = null;
		try {
			con = dbopen.getConnection();

			sql = new StringBuffer();
			sql.append(" SELECT ntrno, id, content, date, noticeno");
			sql.append(" FROM ntreply ");
			sql.append(" WHERE noticeno = ? ");
			sql.append(" ORDER BY ntrno ASC ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				reply = new ArrayList<ReplyDTO>();
				do {
					dto = new ReplyDTO();
					dto.setNtrno(rs.getInt("ntrno"));
					dto.setId(rs.getString("id"));
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getString("date"));
					dto.setNoticeno(rs.getInt("noticeno"));

					reply.add(dto);
				} while (rs.next());
			}

		} catch (Exception e) {
			System.out.println("목록실패 : " + e);
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return reply;
	}// reply() end

	public int replycreate(ReplyDTO dto) {

		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" INSERT INTO ntreply(ntrno, id, content, noticeno) ");
			sql.append(" VALUES ((SELECT IFNULL(MAX(ntrno),0)+1 FROM ntreply as NTR),?, ?, ?) ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNoticeno());

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("추가실패:" + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// replycreate() end

	public int replydelete(int ntrno) {
		int cnt = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" DELETE FROM ntreply ");
			sql.append(" WHERE ntrno=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, ntrno);
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt);
		}
		return cnt;
	}// replydelete() end

	public ReplyDTO replyread(int ntrno) {
		ReplyDTO dto = null;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT ntrno, id, content, date, noticeno ");
			sql.append(" FROM ntreply ");
			sql.append(" WHERE ntrno = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, ntrno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new ReplyDTO();
				dto.setNtrno(rs.getInt("ntrno"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getString("date"));
				dto.setNoticeno(rs.getInt("noticeno"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return dto;
	}// replyread() end

	public int replyupdate(ReplyDTO dto) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE ntreply ");
			sql.append(" SET content = ? ");
			sql.append(" WHERE ntrno=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getNtrno());

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("수정 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// replyupdate() end

	public int replycntup(int noticeno) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE notice ");
			sql.append(" SET replycnt = replycnt+1 ");
			sql.append(" WHERE noticeno=? ");
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, noticeno);

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("댓글 수 증가 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// replycntup() end

	public int replycntdown(int noticeno) {
		int res = 0;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE notice ");
			sql.append(" SET replycnt = replycnt-1 ");
			sql.append(" WHERE noticeno=? ");
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, noticeno);

			res = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("댓글 수 감소 실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		}
		return res;
	}// replycntdown() end

	// 자주하는 질문 bestfaq
	public List bestfaq() throws Exception {
		List list = null;
		sql = new StringBuffer();
		try {
			con = dbopen.getConnection();
			sql.append(" SELECT noticeno, title, readcnt ");
			sql.append(" FROM notice ");
			sql.append(" WHERE code='F' ");

			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList();
				do {
					NoticeDTO dtobestfaq = new NoticeDTO();
					dtobestfaq.setNoticeno(rs.getInt("noticeno"));
					dtobestfaq.setTitle(rs.getString("title"));
					dtobestfaq.setReadcnt(rs.getInt("readcnt"));
					list.add(dtobestfaq);
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("목록실패 : " + e);
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return list;
	}// bestfaq() end

} // class end
