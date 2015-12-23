<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupId, groupDomainName = 'oreumio.com';

    $( document ).ready(function() {

        $( "#index_name" ).click(function( event ) {

            reqFromAnother();
            return false;
        });

        $('#addUser').click(function () {
            addGroup('그룹1');
            addGroupDomain(groupId, groupDomainName);
            login('U1', groupId, 'C1');
            addUser('test1', '테스트1');
            addUser('test2', '테스트2');
            addUser('test3', '테스트3');
            addUser('test4', '테스트4');
            addUser('test5', '테스트5');
            addUser('test6', '테스트6');
            return false;
        });

        $('#testJson').click(function () {
            console.log('test json');
            var data = [{
                k1 : 'v1',
                k2 : 'v2',
                k3 : 'v3-1'
            }];
            $.ajax({
                method: 'post',
                url: "/my/to.do",
                data: JSON.stringify(data),
                success: function (r) {
                    console.log(r);
                },
                dataType: 'json',
                contentType: 'application/json'
            });
        });
    });

    function reqFromAnother() {
        console.log('reqFromAnother');
        $.ajax({
            method: 'post',
            url: "/rest/mail/name",
            success: function (r) {
                console.log(r);
            },
            dataType: 'json'
        });
    }

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
            data: data,
            async: false,
            success: function (r) {
                console.log(r);
                console.log(r.id);
                groupId = r.id;
            }
        });
    }

    function addGroupDomain(groupId, domainName) {
        console.log('addGroupDomain ' + groupId + ', ' + domainName);

        var data = {
            groupId: groupId,
            domainName: domainName
        };

        $.ajax({
            method: 'post',
            url: '/groups/' + groupId + '/domains.do',
            dataType: 'json',
            data: data,
            async: false,
            success: function (r) {
                console.log(r);
                groupDomainName = domainName;
            }
        });
    }

    function login(userId, groupId, clientId) {
        console.log('login');

        var data = {
            userId: userId,
            groupId: groupId,
            clientId: clientId
        };

        $.ajax({
            method: 'post',
            url: "/login.do",
            dataType: 'json',
            data: data,
            success: function (r) {
                console.log(r);
            }
        });
    }

    function addUser(userName, displayName) {
        console.log('addUser');

        var data = {
            userName: userName,
            domainName: groupDomainName,
            password: 'pass',
            quota: 1000000000,
            displayName: displayName
        };

        $.ajax({
            method: 'post',
            url: "/users.do",
            dataType: 'json',
            data: data,
            success: function (r) {
                console.log(r);
            }
        });
    }
</script>
<body>
<h2>Hello OreumIO James REST Server!</h2>

<a href="/rest/mail/first">first</a>
<a id="index_name">name</a>
<p>
<input type="button" id="addUser" value="테스트 사용자 추가" />
</p>
<p>
    <input type="button" id="testJson" value="JSON 테스트" />
</p>
</body>
</html>
