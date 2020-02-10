var main = {
    init: function () {
        console.log("js main start\n");
        var _this = this;
        $('#btn-save-comment').on('click', function () {
            _this.saveComment();
        });
        $('#saveCommentBtn').on('click', function () {
            _this.saveComment2();
        });
    },
    saveComment: function () {
        var data = {
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            console.log("댓글 등록 성공.");
            alert('댓글이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(error + ", " + $('#content').val());
            console.log(error);
        });
    },

    saveComment2: function () {
        console.log("댓글 등록 버튼 클릭.");
    }
};

main.init();