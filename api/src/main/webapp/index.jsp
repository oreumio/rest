<html>
<script src="jquery-2.1.4.js"></script>
<script>
    $( document ).ready(function() {

        $( "#index_name" ).click(function( event ) {

            reqFromAnother();
            return false;
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
</script>
<body>
<h2>Hello World!</h2>

<a href="/rest/mail/first">first</a>
<a id="index_name">name</a>
</body>
</html>
