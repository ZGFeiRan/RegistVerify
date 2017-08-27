<#if pageResult.listData?size &gt; 0 >
    <#list pageResult.listData as item>
        <tr>
            <td>${item.beginDate?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${item.endDate?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${item.publishDate?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${item.position}</td>
            <td>${item.isOverdue}</td>
        </tr>
    </#list>
<#else>
    <tr>
        <td colspan="7" align="center">
            <p class="text-danger">目前没有符合要求的数据</p>
        </td>
    </tr>
</#if>


<script type="text/javascript">
    $(function () {
        $("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
        $("#pagination").twbsPagination({
            totalPages:${pageResult.totalPage},
            currentPage:${pageResult.currentPage},
            initiateStartPageClick: false,
            onPageClick: function (event, page) {
                $("#currentPage").val(page);
                $("#searchForm").submit();
            }
        });
    });
</script>
