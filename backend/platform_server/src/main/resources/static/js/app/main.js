var main = {
    init: function () {
        var _this = this;
        $('#btn-save-comment').on('click', function () {
            _this.saveComment();
        });
    },
    saveComment: function () {
        var data = {
            like_cnt : 0,
            dislike_cnt : 0,
            content: $('#content').val(),
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

    deleteComment: function (idx) {
        var data = {
            idx: idx,
        };
        alert(idx);
/*
        $.ajax({
            type: 'DELETE',
            url: '/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            console.log("댓글 삭제 성공.");
            alert('댓글이 삭제 되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(error + ", " + this.value() + ", " + idx);
            console.log(error);
        });*/
    }
};

main.init();