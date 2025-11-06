<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="../css/table.css">
<style type="text/css">
.board_row{
  margin: 0px auto;
  max-width: 800px;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
let bCheck=true
$(function(){
	$("#delSpan").text("삭제")
	$('#delSpan').click(function(){
		if(bCheck===true)
		{
			$('#delTr').show("slow")
			$("#delSpan").text("취소")
			bCheck=false
		}
		else
		{
			$('#delTr').hide("slow")
			$("#delSpan").text("삭제")
			bCheck=true
		}
	})
	$('#delBtn').on('click',function(){
		let pwd=$('#delpwd').val()
		if(pwd.trim()==="")
		{
			$('#delpwd').focus()
			return
		}
		let no=$('#delBtn').attr("data-no")
		let page=$('#delBtn').attr("data-page")
		// 서버와 연결 => axios / fetch / getJSON
		/*
		  axios.get('url',{
			  params:{}
		  })
		  .then(result=>{
			  결과값 
		  })
		  axios.post
		*/
		$.ajax({
			type:'post',
			url:'../board/delete.do',
			data:{"no":no,"pwd":pwd},
			success:function(result)
			{
				// yes/no 
				if(result==="yes")
				{
					location.href="../board/list.do?page="+page
				}
				else
				{
					alert("비밀번호가 틀립니다!!")
					$('#delpwd').val("")
					$('#delpwd').focus()
				}
			},
			error:function(error)
			{
				console.log(error)
			}
		})
	})
})
</script>
</head>
<body>
<!-- ****** Breadcumb Area Start ****** -->
    <div class="breadcumb-area" style="background-image: url(../img/bg-img/breadcumb.jpg);">
        <div class="container h-100">
            <div class="row h-100 align-items-center">
                <div class="col-12">
                    <div class="bradcumb-title text-center">
                        <h2>내용보기</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="breadcumb-nav">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <%-- 검색기 --%>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
    <!-- ****** Breadcumb Area End ****** -->

    <!-- ****** Archive Area Start ****** -->
    <section class="archive-area section_padding_80">
        <div class="container">
            <div class="row board_row">
             <table class="table">
              <tr>
               <th width=20% class="text-center">번호</th>
               <td width=30% class="text-center">${vo.no }</td>
               <th width=20% class="text-center">작성일</th>
               <td width=30% class="text-center">${vo.dbday }</td>
              </tr>
              <tr>
               <th width=20% class="text-center">이름</th>
               <td width=30% class="text-center">${vo.name }</td>
               <th width=20% class="text-center">조회수</th>
               <td width=30% class="text-center">${vo.hit }</td>
              </tr>
              <tr>
               <th width=20% class="text-center">제목</th>
               <td colspan="3">${vo.subject }</td>
              </tr>
              <tr>
                <td colspan="4" valign="top" height="200">
                 <pre style="white-space: pre-wrap;border:none;background-color: white;">${vo.content }</pre>
                </td>
              </tr>
              <tr>
               <td colspan="4" class="text-right">
                <a href="../board/update.do?no=${vo.no }&page=${page}" class="btn btn-mint btn-xs">수정</a>
                <span class="btn btn-pink btn-xs" id="delSpan">삭제</span>
                <a href="../board/list.do?page=${page }" class="btn btn-mint btn-xs">목록</a>
               </td>
              </tr>
              <tr id="delTr" style="display:none">
                <td colspan="4" class="text-right">
                 비밀번호:<input type=password name=pwd
                        style="width:100px" 
                        class="input-sm" id="delpwd">
                   <input type=button class="btn-mint btn-sm"
                    id="delBtn" value="삭제"
                    data-no="${vo.no }" data-page="${page }"
                   >
                </td>
              </tr>
             </table>
            </div>
            <div class="row board_row" style="margin-top: 20px;">
  <div class="col-md-10 col-md-offset-1">
    <h3 class="text-primary" style="border-bottom: 2px solid #337ab7; padding-bottom: 8px;">
      <span class="glyphicon glyphicon-comment"></span> 댓글
    </h3>

    <!-- 댓글이 없는 경우 -->
    <c:if test="${rCount==0}">
      <div class="alert alert-info text-center" style="margin-top:15px;">
        <span class="glyphicon glyphicon-info-sign"></span> 아직 등록된 댓글이 없습니다.
      </div>
    </c:if>

    <!-- 댓글이 있는 경우 -->
    <c:if test="${rCount>0}">
      <c:forEach var="rvo" items="${rList}">
        <div class="panel panel-default" style="margin-top:15px;">
          <div class="panel-heading" style="background-color:#f7f7f7;">
            <div class="row">
              <div class="col-md-8 col-sm-8 text-left">
                <strong>${rvo.name}</strong>
                <small class="text-muted">(${rvo.dbday})</small>
              </div>
              <div class="col-md-4 col-sm-4 text-right">
                <c:if test="${sessionScope.id!=null}">
                  <c:if test="${sessionScope.id==rvo.id}">
                    <a href="#" class="btn btn-xs btn-info">
                      <span class="glyphicon glyphicon-pencil"></span> 수정
                    </a>
                    <a href="#" class="btn btn-xs btn-warning">
                      <span class="glyphicon glyphicon-trash"></span> 삭제
                    </a>
                  </c:if>
                  <a href="#" class="btn btn-xs btn-success">
                    <span class="glyphicon glyphicon-share-alt"></span> 답글
                  </a>
                </c:if>
              </div>
            </div>
          </div>
          <div class="panel-body" style="background-color: #fcfcfc;">
            <pre style="white-space: pre-wrap; background-color: transparent; border:none; font-size:14px;">
${rvo.msg}
            </pre>
          </div>
        </div>
      </c:forEach>
    </c:if>

    <!-- 댓글 입력창 -->
    <c:if test="${sessionScope.id!=null}">
      <div class="panel panel-primary" style="margin-top:20px;">
        <div class="panel-heading">
          <strong>댓글쓰기</strong>
        </div>
        <div class="panel-body">
          <form method="post" action="reply_insert.do">
            <div class="form-group">
              <textarea class="form-control" rows="4" name="msg" placeholder="댓글을 입력하세요..." style="resize:none;"></textarea>
            </div>
            <button type="submit" class="btn btn-primary btn-block" style="font-weight:bold;">
              <span class="glyphicon glyphicon-send"></span> 등록하기
            </button>
          </form>
        </div>
      </div>
    </c:if>

    <!-- 로그인 안된 경우 -->
    <c:if test="${sessionScope.id==null}">
      <div class="alert alert-warning text-center" style="margin-top:15px;">
        <span class="glyphicon glyphicon-lock"></span> 댓글을 작성하려면 로그인하세요.
      </div>
    </c:if>
  </div>
</div>
            
        </div>
    </section>
</body>
</html>