<%--
  Created by IntelliJ IDEA.
  User: jchoi
  Date: 12/18/15
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>file upload</title>
    <script src="jquery-2.1.4.js"></script>
</head>
<body>

<form id="frm">
    <input type="file" id='file1' name="file1" />
    <input type="file" id='file2' name="file2" />
    <input type="button" id="upload" value="클릭" />
</form>


</body>
</html>

<script>
    $(document).ready(function() {

        $('#upload').click(function () {
            var data = new FormData();
            data.append('file1', $('#file1')[0].files[0] );
            data.append('file2', $('#file2')[0].files[0] );
            $.ajax({
                url: '/file/upload.do',
                processData: false,
                contentType: false,
                data: data,
                type: 'POST',
                success: function (result) {
                    alert("업로드 성공!!");
                    console.log(result);
                }
            });
        });

        console.log('file upload ready');
    });
</script>
