// btn-> 当前的a标签对象
function like(btn, entityType, entityId, entityUserId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType, "entityId":entityId, "entityUserId":entityUserId},
        function(data) {
            // 将json字符串转化为json对象
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 修改赞的数量和状态
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':'赞');
            } else {
                alert(data.msg);
            }
        }
    )

}