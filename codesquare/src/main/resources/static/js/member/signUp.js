/**
 * 회원가입, 회원정보 수정 시 정보 체크
 */



var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});


	
	
var idck = 0;
var emailck = 0;
var pwck = 0;

$("#idValidate").click(function() {

	var userId = $("#userId").val();
	var regExp = /^[a-z0-9]{4,15}$/;

	if (regExp.test(userId)) {
		idCheck();
	} else {
		$("#idCheckMsg").css("color", "red");
		$("#idCheckMsg").text("아이디는 영문자,숫자로 이루어진 4자이상 15자 미만");
		$("#userId").focus();
	}

});

function idCheck() {

	var userId = $("#userId").val();
	
	$.ajax({
		type : "post",
		data : userId,
		url : "idCheck",
		contentType : "application/json; charset=UTF-8",
		success : function(response) {
			if ( response > 0) {

				$("#idCheckMsg").css("color", "red");
				$("#idCheckMsg").text("이미 존재하는 아이디 입니다.");
				$("#userId").focus();

			} else {
				$("#idCheckMsg").css("color", "blue");
				$("#idCheckMsg").text("사용가능한 아이디입니다.");

				// 아이디가 중복하지 않으면 
				idck = 1

			}
		},
		error : function(error) {
			$("#idCheckMsg").css("color", "black");
			$("#idCheckMsg").text("error");
		}
	});

};



$("#emailValidate").click(function() {

	var email = $("#email").val();
	var regExp = /[a-zA-Z0-9-_]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

	if (regExp.test(email)) {
		emailCheck();
	} else {
		$("#emailCheckMsg").css("color", "red");
		$("#emailCheckMsg").text("올바르지않은 이메일 양식입니다.");
		$("#email").focus();
	}

});

function emailCheck() {

	
	var email = $("#email").val();
	
	
	$.ajax({
		type : "post",
		data : email,
		url : "emailCheck",
		contentType : "application/json; charset=UTF-8",
		success : function(response) {

			if (response > 0) {

				alert(response);
				$("#emailCheckMsg").css("color", "red");
				$("#emailCheckMsg").text("이미 존재하는 이메일 입니다.");
				$("#email").focus();

			} else {

				$("#emailCheckMsg").css("color", "blue");
				$("#emailCheckMsg").text("사용 가능한 이메일 입니다.");
				emailck = 1;

			}
		},
		error : function(error) {
			$("#emailCheckMsg").css("color", "black");
			$("#emailCheckMsg").text("error");
		}
	});

};

// 비밀번호 체크

$("#password").blur(function() {
	
	var password = $("#password").val();
	var regExp = /^[a-zA_Z0-9]{4,15}$/;
	
	if (regExp.test(rePassword)) {
		$("#pwCheckMsg").text("");
	} else {
		$("#pwCheckMsg").css("color", "red");
		$("#pwCheckMsg").text("비밀번호는 영 대, 소문자, 숫자 로 이루어진 4자 이상 15자 미만");
		$("#password").focus();
	}

});

$("#rePassword").blur(function() {
	
	var password = $("#password").val();
	var rePassword = $("#rePassword").val();

	if ((password != null && rePassword != null) && password == rePassword) {
		$("#pwCheckMsg").text("");
		pwck = 1;
	} else {
		$("#pwCheckMsg").css("color", "red");
		$("#pwCheckMsg").text("비밀번호를 확인해주세요.");
		$("#rePassword").focus();
	}
});

// 회원가입 버튼
$("#signUp").click(function() {

	if (idck == 1 && emailck == 1 && pwck == 1) {
		if ($("#agree").is(":checked")) {
			$("#signUpForm").attr('action', "signUp").submit();
		} else {
			alert("체크해주세요.");
		}
	} else {
		alert(idck+","+emailck+","+pwck);
		alert("입력한 정보를 확인해주세요.");
	}
});


