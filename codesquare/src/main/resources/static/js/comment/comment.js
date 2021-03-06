/**
 * 
 */
var bno = $("#bno").val(); 

$(document).ready(function(){
    commentList(); //페이지 로딩시 댓글 목록 출력 
    //게시글 번호
});
$('[name=commentInsertBtn]').click(function(){ //댓글 등록 버튼 클릭시 
    var insertData = $('[name=commentInsertForm]').serialize(); //commentInsertForm의 내용을 가져옴
    commentInsert(insertData); //Insert 함수호출(아래)
});

//댓글 목록 
function commentList(){
    $.ajax({
        url : '/comment/list',
        type : 'get',
        data : {'bno':bno},
        success : function(data){
            var a =''; 
            $.each(data, function(key, value){ 
                a += '<div class="commentArea" style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
                a += '<div class="commentInfo'+value.cno+'"> 작성자 :'+value.writer
                a += '<div align="right">'+value.writeDateBoard+'</div>'
                a += '<p class="commentContent'+value.cno+'">'+value.content +'</p>';
                a += '<div align="right">'
                a += '<a onclick="commentUpdate('+value.cno+',\''+value.content+'\');" style="background-color: #9370DB" class="btn btn-outline-light text-dark"> 수정 </a>';
                a += '<a onclick="commentDelete('+value.cno+');" style="background-color: #9370DB"; class="btn btn-outline-light text-dark"> 삭제 </a> </div>';
                a += '</div></div>';
                a += '</div>';
            });
            
            $(".commentList").html(a);
        }
    });
}
//댓글등록
function commentInsert(insertData){
    $.ajax({
        url : '/comment/insert',
        type : 'post',
        data : insertData,
        success : function(data){
            if(data == 1) {
                commentList(); //댓글 작성 후 댓글 목록 reload
                $('[name=content]').val('');
            }
        }
    });
}
// 댓글 삭제

function commentDelete(cno){
    $.ajax({
        url : '/comment/delete/'+cno,
        type : 'post',
        success : function(data){
            if(data == 1) commentList(bno); //댓글 삭제후 목록 출력 
        }
    });
}

function commentUpdate(cno, content){
    var a ='';
    
    a += '<div class="input-group">';
    a += '<input type="text" class="form-control" name="content_'+cno+'" value="'+content+'"/>';
    a += '<span class="input-group-btn"><button class="btn btn-default" type="button" onclick="commentUpdateProc('+cno+');">수정</button> </span>';
    a += '</div>';
    $('.commentContent'+cno).html(a);
}
 
//댓글 수정
function commentUpdateProc(cno){
    var updateContent = $('[name=content_'+cno+']').val();
    
    $.ajax({
        url : '/comment/update',
        type : 'post',
        data : {'content' : updateContent, 'cno' : cno},
        success : function(data){
            if(data == 1) commentList(bno); //댓글 수정후 목록 출력 
        }
    });
}
