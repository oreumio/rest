<%--
  Created by IntelliJ IDEA.
  User: jchoi
  Date: 12/18/15
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<head>
    <title>mail send</title>
</head>
<body>
<input type="button" id="send" value="메일 보내기" />

</body>
</html>
<script>
    $(document).ready(function() {

        console.log($('#send'));

        $('#send').click(function () {
            console.log('clicked');

            var data = {};

            data.subject = '테스트 메일입니다.';
            data.priority = '';
            data.separateSendYn = 'Y';
            data.massiveMailYn = '';
            data.password = '';
            data.passwordYn = '';
            data.content = '안녕하세요, 테스트 메일입니다.';
            data.mailFrom = {personal: '테스트원', address: 'test1@oreumio.com'};
            data.mailTos = [{personal: '테스트투', address: 'test2@oreumio.com'}, {personal: '테스트쓰리', address: 'test3@oreumio.com'}];
            data.mailCcs = [{address: 'test4@oreumio.com'}, {address: 'test5@oreumio.com'}];
            data.mailBccs = [{address: 'test6@oreumio.com'}];
            data.fileList = [];
            data.fileDownEndDe = '';
            data.mode = 'write';
            data.afterPage = '';
            data.reservDt = '';
            data.reservTime = '';
            data.timeZoneId = '';
            data.mailIdList = [{mailboxName: 'Drafts', uid: 1}];
            data.storeExpireDe = '';
            data.mailReadRecpinYn = '';

            $.ajax({
                url: '/send/send.do',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json',
                type: 'POST',
                success: function (result) {
                    alert("메일 보내기 성공!!");
                    console.log(result);
                }
            });
        });

        console.log('mail send ready');
    });
</script>