<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<#import "parts/about.ftl" as a>
<#import "parts/login.ftl" as l>


<@c.page>

    <h5> Hello, ${name}</h5>
    <div class="layer1">     <p> <font size="3"face ="helvetica">                   This is my simple spring boot app. You can find here some basic user options like registration( you need email to activate your account, </p>   </div>

    <style>

        .layer {
            margin-top: -10px;
        }
        .layer1 {
            margin-top: 18px;
        }

    </style>


    <div class="layer"  >  <p> <font size="3" face ="helvetica">  or you can take demo account), data validation, profile editing and  for admins only there is a user editing panel </p>     </div>

    <div class="layer1"  >  <p> <font size="3" face ="helvetica"> You can leave a message and find it by parameter <a href="main" class="myref"><font size="4">here</a></p> </div>


    <div class="layer1"  > <p> <font size="3"face ="helvetica"> Also site contains my simple game on javascript <a href="game" class="myref"><font size="4">here</a></p>             </div>




    <@a.about />

</@c.page>



