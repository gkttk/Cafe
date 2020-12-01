<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="by" scope="session"/>
<fmt:bundle basename="i18n/message">

    <html>
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="index.title"/></title>
        <link rel="stylesheet" type="text/css" href="static/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
        <script type="text/javascript" src="static/js/script.js"></script>
    </head>
    <body>
    <div class="wrapper">
        <%@ include file="WEB-INF/view/parts/header.jsp" %>

        <main>
            In urna felis dolor curabitur eleifend parturient. Nisi interdum accumsan penatibus vehicula orci mattis
            dignissim egestas fringilla tortor ligula ad? Turpis turpis amet mauris ultricies, semper posuere. Rhoncus
            cras turpis rhoncus. Amet laoreet interdum justo commodo curabitur fames feugiat mi, imperdiet conubia.
            Himenaeos auctor, himenaeos commodo non. Etiam blandit dignissim ante tristique aenean dolor fames nec?
            Pretium vitae lorem sagittis nisi imperdiet per tristique ut dictum pharetra magnis. Turpis integer pharetra
            faucibus magnis nascetur mattis sem accumsan! Litora mi cum penatibus fusce iaculis lacinia. Tristique class
            hendrerit.

            Bibendum luctus integer ut netus pharetra vehicula posuere duis netus risus. Sit sociosqu ligula nulla,
            metus placerat tristique. Mollis velit accumsan leo nullam arcu turpis lectus ornare cras interdum euismod.
            Erat diam massa class lobortis sociosqu consectetur dis semper class? Fames purus vitae varius erat nulla
            neque interdum. Diam potenti mus rutrum elementum magna vel volutpat. Laoreet nam nascetur sapien himenaeos
            odio nullam urna dis bibendum metus platea libero. Erat vitae lorem nunc massa iaculis duis. Litora
            tristique augue phasellus leo hendrerit quisque ullamcorper. Lacinia.

            Quis cubilia parturient volutpat vulputate bibendum! Tincidunt est vehicula pulvinar in sollicitudin.
            Egestas sapien lectus; pretium quis velit euismod. Habitant class accumsan netus molestie mus elementum
            conubia orci volutpat. Nec leo turpis cum, rutrum euismod magnis. Vulputate accumsan tortor dignissim dis
            odio leo. Sodales dictumst facilisis tristique felis suspendisse adipiscing convallis blandit blandit netus?
            Cursus risus magnis lectus malesuada vivamus lacus dictum aliquam leo sodales? Diam interdum molestie
            facilisis suspendisse cum, dignissim porttitor proin diam neque placerat. Magna nostra mollis laoreet
            maecenas viverra himenaeos elementum aptent ac.

            Litora curabitur porttitor urna ligula venenatis condimentum nibh platea et eu maecenas libero. Et conubia
            tempus ipsum ante accumsan eu ultrices velit massa, diam turpis? Ridiculus nunc felis a sed? Lectus tempus
            dapibus augue egestas facilisis cursus primis in dis at ante curae;. Netus vivamus blandit ante porttitor.
            Nunc ultricies diam diam platea magnis posuere, vehicula sociosqu nascetur duis nam sodales. Tellus quam
            praesent mollis sollicitudin cubilia vivamus. Maecenas taciti mauris vitae ad. Porta faucibus class,
            dictumst torquent nullam. Interdum sociosqu, metus maecenas nostra euismod lacus nibh dictumst potenti
            laoreet?

            Eleifend vestibulum varius lectus placerat dis, rhoncus hac auctor metus amet. A auctor; tristique elit
            laoreet ligula. Convallis erat nullam nunc habitant nascetur, vestibulum elit. Sapien eleifend mi a
            pellentesque nisl, scelerisque eget sem sollicitudin sociis leo accumsan. Nascetur nam montes neque leo eget
            malesuada donec. Torquent laoreet bibendum aptent fusce scelerisque cubilia convallis gravida ultricies
            vestibulum. Varius sodales vitae tristique. Nunc neque netus, quisque diam at placerat. Nulla habitasse
            suscipit vitae orci etiam torquent. Dolor vivamus nec venenatis.
        </main>
        <%@ include file="WEB-INF/view/parts/footer.jsp" %>
    </div>
    </body>

</fmt:bundle>

</html>
