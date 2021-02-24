define(["text!/view/element/menu.html"], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                menuList: [],
                showData: [],
                expandKeys: [],
                keyword: '',
                editFormConfig: {
                    formLabelWidth: '120px',
                    visible: false,
                    isAdd: true,
                    title: '',
                    parent: ""
                },
                editForm: {}
            }
        },
        created: function () {
            this.initMenuTree()
        },
        methods: {
            initMenuTree: function () {
                Tools.http.get('/api/menu/tree')
                    .then(res => {
                        this.menuList = res.data
                        this.showData = res.data
                    })
            },
            handleAdd(parentNode) {
                this.editForm = {}
                this.editFormConfig.title = '新增菜单'
                this.editFormConfig.parent = parentNode ? parentNode.code + ' -- ' + parentNode.name : '--'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
                this.editForm.parentId = parentNode ? parentNode.id : 0
            },
            handleEdit(node) {
                this.editForm.id = node.id
                this.editForm.code = node.code
                this.editForm.name = node.name
                this.editForm.url = node.url
                this.editForm.icon = node.icon
                this.editForm.orderNum = node.orderNum
                this.editForm.remark = node.remark

                this.editFormConfig.title = '修改菜单'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let method = this.editFormConfig.isAdd ? 'post' : 'put'
                Lit.httpRequest.request(method, '/api/menu', this.editForm).then(res => {
                    if (res.success) {
                        this.$message.success(this.editFormConfig.title + '成功')
                        this.initData()
                    } else {
                        this.$message.error(res.message)
                    }
                })
                this.editFormConfig.visible = false
            },
            handleDelete(id) {

            },
            handleChange(id) {
            },
            handleSearch() {
                this.expandKeys = []
                this.showData = this.filterNode(this.keyword, this.menuList)
            },
            filterNode(keyword, itemList) {
                if (!itemList) {
                    return []
                }
                return itemList.map(item => Object.assign({}, item)).filter(item => {
                    if (item.children) {
                        item.children = this.filterNode(keyword, item.children)
                    }
                    if (item.children && item.children.length > 0) {
                        this.expandKeys.push(item.id + '')
                        return true
                    }
                    return this.isNodeMatch(keyword, item)
                })
            },
            isNodeMatch(keyword, item) {
                if (keyword) {
                    return (item.code && item.code.indexOf(keyword) !== -1)
                        || (item.name && item.name.indexOf(keyword) !== -1)
                        || (item.remark && item.remark.indexOf(keyword) !== -1)
                }
                return true;
            }
        }
    }
})