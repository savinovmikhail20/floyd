<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">





    <div class="form-row">
    <div class="form-group col-md-6">


    <form method="get" action="/main/" class="form-inline">
        <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search">
        <button type="submit" class="btn mybutton ml-2">Найти по тэгу</button>
    </form>



    </div>
</div>


    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main/from" class="form-inline">
                <input type="text" name="time" class="form-control" value="${time?ifExists}" placeholder="Search">
                <button type="submit" class="btn mybutton ml-2">По времени </button>
            </form>
        </div>
    </div>
    <a class="btn mybutton" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>

    <div class="collapse <#if message??>show</#if>"" id="collapseExample">
        <div class="form-group mt-3"  >
            <form method="post">
                <div class="form-group">
                    <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.text}</#if>" name="text" placeholder="Введите сообщение" />
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" name="tag" class="form-control"  placeholder="Тэг"
                    value="<#if message??>${message.tag}</#if>" name="text" placeholder="Введите тэг" />
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>


                <input type="hidden" name="_csrf" value="${_csrf.token}" />

                <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
        </form>


    </div>






    <table class="table table-bordered   mt-2  ">
        <thead class="thead-dark ">
        <tr>

            <th scope="col "  style="width: 6%">#</th>
            <th scope="col" style="width: 9%">Author</th>
            <th scope="col" style="width: 9%">Tag</th>
            <th scope="col" style="width: 50%">Text</th>
            <th scope="col" style="width: 9%"> Time </th>
            <th scope="col" style="width: 9%"> Ref </th>
            <th scope="col" style="width: 7%">   <div title="Click if you want to get notifications about new messages ">
                    <form method="get" action="/main/getNews">
                        <button <#if news >    class="fa fa-paper-plane btn btn-primary btn-md  rounded-3 "    <#else> class= "btn btn-light   fa fa-paper-plane "   </#if>
                                type="submit" data-toggle="tooltip" data-placement="top" ><i aria-hidden="true"></i></button>
                    </form>
                </div> </th>



        </tr>
        </thead>
        <#list messages as message >
            <thead>
            <tr>
                <th scope="col style="width: 16.66%">${message?counter}</th>
                <th scope="col">${message.authorName}</th>
                <th scope="col"> ${message.tag}   </th>
                <th scope="col"> ${message.text}   </th>
                <th scope="col"> ${message.localDateTime}   </th>
                <th scope="col"><h4 ><a class="myref" href=${message.text}> Reference  </a></h4></th>
                <th scope="col">
                    <form method="get" action="/main/del/${message.id}">
                    <ul class="list-inline m-0">


                        <li class="list-inline-item">
                            <button class="btn btn-danger btn-sm rounded-3" type="submit" data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times" aria-hidden="true"></i></button>
                        </li>
                    </ul>
                    </form>



                </th>

            </tr>
            </thead>


        </#list>
    </table>


    </tbody>
    </table>


</@c.page>