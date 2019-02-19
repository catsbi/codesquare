var stompClient = null;
var socket = null;
// var messageList = null;
var messages = $("[name=messages]").val();

var aline;
$(function() {

	//var messageList = $("#messages");
	 socket = new SockJS('/stomp');
	 stompClient = Stomp.over(socket);

	// 연결시점
	stompClient.connect({}, function(frame) {
		console.log("연결되었음");
		
		//DB List메소드
		noticeviewList();
		
		stompClient.subscribe("/topic/message", function(data) {
			var message = data.body;
			//console.log("message값 : " + message);
			//console.log("aline값 : " + aline);
			//console.log("메세지리스트 : " + messageList);
			//var notibell = "<img src='https://previews.123rf.com/images/hanaschwarz/hanaschwarz1211/hanaschwarz121100002/16260237-%ED%9D%B0%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%EA%B3%A0%EB%A6%BD-%EB%B9%A8%EA%B0%84-%EB%A6%AC%EB%B3%B8%EA%B3%BC-%EC%A2%85.jpg'/>"; 
		 
			//기존
			$('#messages').prepend("<li>" +message+ "</li>");
			//$("#messages").prepend("<li>" +notibell +message+ "</li>");
			//$('#messages').prepend("<li>" +"<a href='" + aline + "'>"+ "회원님의 게시판에 댓그리달렸습니다" +message+"</a>"+ "</li>");
			//console.log("66666=== "+"<li>" + "<a href='" + ada + "'>" + notibell +message+ "</a>"+"</li>");
			
			//a태그 추가 시
			//if(!(typeof aline === 'undefined' || aline===null)){
			//if(!(typeof aline === 'undefined')){
			//	console.log("연결안의 aline경로 마지막나올애: " + aline)
			//+이미지	messageList.prepend("<li>" + "<a href='" + aline + "'>" + notibell +message+ "</a>"+"</li>");
			//messageList.prepend("<li>" +"<a href='" + aline + "'>" +message+ "</a>"+"</li>");
			//}
			 
			//ajax 알림리스트 DB뿌리기 1
			/*function addNoticeContent(){
				$.ajax({
					url:'noticeview',
					type:'GET',
					data:{"qq":qq, "aline":aline}
				}).done(function(data){
					var sHTML=data;
					console.log(data);
				//	oEditors.getById["notepad"].exec("SET_IR", [""]); //내용초기화
				//	oEditors.getById["notepad"].exec("PASTE_HTML", [sHTML]); //내용밀어넣기
				}).fail(function(data){
					if(data!=1){
						alert("노티스 리스트 ajax Load Fail");
					}
				});
			}*/
			
			
		}); //subscribe 닫음
	});//connect 닫음
}); //레디 function


//ajax 알림리스트 DB 2
function noticeviewList() {
	$.ajax({
		url:'noticeview',
		type:'GET',
		data:{"messages":messages}
	}).done(function(data){
		var noticeviewContent =""; 
		$.each(data, function(key, value){
			noticeviewContent += "<li>"+value.noticeContent+value.sendDate+"<a href='"+value.noticeLink+"'</a>"+"</li>";
			//	 $("#messages").prepend("<li>"  +messages+ "</li>");
			// $(this).append(noticeviewContent);
		});
		console.log("noticeviewList나옴")
		// $(".review-container").append(reviewContent);
		$("#messages").html(noticeviewContent);
	}).fail(function(data){
		console.log("noticeviewContent실패");
	})
}

/*//ajax 알림리스트 DB뿌리기 2
function noticeviewList() {
	$.ajax({
		url:'noticeview',
		type:'GET',
		data:{"qq":qq, "aline":aline}
	}).done(function(data){
		var noticeviewContent =""; 
		$.each(data, function(key, value){
			noticeviewContent+="<p>"+"리스트가 나오는 곳입니다"+"</p>";
		});
		
		// $(".review-container").append(reviewContent);
		$("#messages").html(noticeviewContent);
	}).fail(function(data){
		alert("noticeviewContent 실패");
	})
}//noticeviewList() 닫음
*/

//댓글 추가시
	function reply() {
		
		aline = window.location.href;
		
		$.ajax({
			type : 'get',
			//dataType:"json", 
			url : '/send/message',
			data : {
				'qq' : "qq3회원님의 게시판에 댓글이 달렸습니다.",
				'aline' : aline
			},

			success : function() {
				
				console.log("reply()작동 ok");
				 
				//aline = window.location.href;
				console.log("atag()실행해서 경로 가지고 옴" +aline);
				//a태그 성공$('#messages').prepend("<li>" +"<a href='" + aline + "'>"+ "댓글달림. 링크타고 가기 클릭</a>"+ "</li>");
				//$('#messages').prepend("<li>" +"<a href='" + aline + "'>"+ "회원님의 게시판에 댓그리달렸습니다" +"</a>"+ "</li>");
				//console.log("data.body: " +message);
				//데이터베이스 가져오기 연습
				$('#messages').prepend("<li>" +message+"</li>");
				
			}//success 닫음
		}); //ajax 닫음
	}//reply() 닫음

	
//회원가입시 알림발송
	function join() {
		aline = null;
		$.ajax({
			type : 'get',
			url : '/send/message',
			data : {
				'qq' : "ggg가입을 축하합니다!!!",
				'aline' : aline
			},
			success : function(data) {
				//message = data.body; 
				console.log("가입햇다.");

				$('#messages').prepend("<li>" +message+ "</li>");
			}
		});
	}//join() 닫음
	
//모두 삭제
	function allDelete() {
		$( "#messages" ).empty();
	}

//읽음삭제
	function readDelete() {
		//
	}
	
//닫힘
	function onclose() {
		alret("연결이 끊겼습니다");
		
	}
	
//연결끊기
	function disconnect() {
	    if (stompClient !== null) {
	        stompClient.disconnect();
	    }
	    
	    console.log("Disconnected");
	}
	
