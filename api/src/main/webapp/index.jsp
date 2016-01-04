<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupId, groupDomainName = 'localhost:8083';

    $( document ).ready(function() {

        $('#addUser').click(function () {
            addGroup('그룹1');
            addGroupDomain(groupId, groupDomainName);
            addUser('test1', groupDomainName, groupId, '테스트1');
            addUser('test2', groupDomainName, groupId, '테스트2');
            addUser('test3', groupDomainName, groupId, '테스트3');
            addUser('test4', groupDomainName, groupId, '테스트4');
            addUser('test5', groupDomainName, groupId, '테스트5');
            addUser('test6', groupDomainName, groupId, '테스트6');
            return false;
        });
    });

    function addGroup(displayName) {
        console.log('addGroup');

        var data = {
            quota: 1000000000,
            displayName: displayName
        };

        $.ajax({
            method: 'post',
            url: "/groups.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa('sa' + ":" + 'letmein'));
            },
            success: function (r) {
                console.log(r);
                console.log(r.id);
                groupId = r.id;
            }
        });
    }

    function addGroupDomain(groupId, groupDomainName) {
        console.log('addGroupDomain ' + groupId + ', ' + groupDomainName);

        var data = {
            groupId: groupId,
            groupDomainName: groupDomainName
        };

        $.ajax({
            method: 'post',
            url: '/groups/' + groupId + '/domains.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa('sa' + ":" + 'letmein'));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function addUser(userName, groupDomainName, groupId, displayName) {
        console.log('addUser');

        var data = {
            userName: userName,
            host: groupDomainName,
            groupId: groupId,
            password: 'pass',
            quota: 1000000000,
            displayName: displayName
        };

        $.ajax({
            method: 'post',
            url: "/users.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa('sa' + ":" + 'letmein'));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }
</script>
<body>
<h2>Hello OreumIO James REST Server!</h2>
<p>
<input type="button" id="addUser" value="테스트 사용자 추가" />
</p>
</body>
</html>
