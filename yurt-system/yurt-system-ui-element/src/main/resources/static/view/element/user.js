define(["text!/view/element/user.html"], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                queryForm: {
                    pageNum: 1
                },
                pageList: [],
                keyword: ''
            }
        },
        created: function () {
            this.initData()
        },
        methods: {
            initData: function () {
                Tools.http.get('/api/user/list')
                    .then(res => {
                        this.pageList = res.data
                    })
            },
            handleSearch() {},
            handlePageNumChange(pageNum) {
                this.queryForm.pageNum = pageNum
                this.initData()
            }
        }
    }
})