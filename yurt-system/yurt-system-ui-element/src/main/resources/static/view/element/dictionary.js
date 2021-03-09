define(["text!/view/element/dictionary.html"], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                queryForm: {
                    parentId: this.$route.query.parentId || 0,
                    pageNum: 1,
                    pageSize: 20
                },
                page: [],
                navData: [],
                expandKeys: [],
                keyword: '',
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: '',
                    parent: ""
                },
                editForm: {}
            }
        },
        created: function () {
            console.log(this.$route.query)
            this.initData()
        },
        methods: {
            initData: function () {
                Tools.http.get('/api/dict/list', this.queryForm)
                    .then(res => this.page = res.data)
            },
            handleSearch: function () {

            },
            handleAdd() {
                this.editForm = { parentId: this.queryForm.parentId }
                this.editFormConfig.title = '新增字典'
                var len = this.navData.length
                this.editFormConfig.parent = len > 0 ? this.navData[len - 1].dictKey : '--'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
            },
            handleEdit(node) {
                this.editForm = Object.assign({}, node)
                this.editFormConfig.title = '修改字典'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let url = '/api/dict'
                let editRes = this.editFormConfig.isAdd ? Tools.http.postJson(url, this.editForm)
                    : Tools.http.putJson(url, this.editForm)
                editRes.then(res => {
                    this.$message.success(this.editFormConfig.title + '成功')
                    this.initData()
                    this.editFormConfig.visible = false
                })
            },
            handleRowClick: function (row) {
                this.queryForm.parentId = row.id
                var navItem = Object.assign({}, row)
                navItem.idx = this.navData.length
                this.initData()
                this.navData.push(navItem)
            },
            handleNavClick(row) {
                this.queryForm.parentId = row.id
                if (row.idx == -1) {
                    this.navData = []
                } else {
                    this.navData = this.navData.slice(0, row.idx + 1)
                }
                this.initData()
            },
            handlePageNumChange(pageNum) {
                this.queryForm.pageNum = pageNum
                this.initData()
            },
            handleDelete(node) {
                this.$confirm('确定删除字典' + node.dictKey, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(_ => Tools.http.delete('/api/dict/' + node.id))
                    .then(res => {
                        this.$message.success('删除字典成功')
                        this.initData()
                    })
            }
        }
    }
})