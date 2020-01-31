var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            userId: $('#userId').val(),
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
            alert(error);
            console.log(error);
        });
    }
};

main.init();