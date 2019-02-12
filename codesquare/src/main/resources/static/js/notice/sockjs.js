var stompClient = null;
var socket = null;
// var messageList = null;

$(function() {
	var messageList = $("#messages");
	 socket = new SockJS('/stomp');
	 stompClient = Stomp.over(socket);

	// 연결시점
	stompClient.connect({}, function(frame) {
		console.log("연결되었음");

		stompClient.subscribe("/topic/message", function(data) {
			var message = data.body;
			messageList.append("<li class='notification'>" +"<img src='https://previews.123rf.com/images/hanaschwarz/hanaschwarz1211/hanaschwarz121100002/16260237-%ED%9D%B0%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%EA%B3%A0%EB%A6%BD-%EB%B9%A8%EA%B0%84-%EB%A6%AC%EB%B3%B8%EA%B3%BC-%EC%A2%85.jpg'/>"+ message+ "</li>");
			console.log("li ok");
		});

	});
});
	function reply() {
		$.ajax({
			type : 'get',
			url : 'send/message',
			data : {
				'message' : "댓글보세요"
			},

			success : function() {
				console.log("ajax함수 ok")
			}
		});
	}
