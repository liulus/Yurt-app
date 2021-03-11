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
                this.editForm = { type: 'menu' }
                this.editFormConfig.title = '新增菜单'
                this.editFormConfig.parent = parentNode ? parentNode.name : '--'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
                this.editForm.parentId = parentNode ? parentNode.id : 0
            },
            handleEdit(node) {
                this.editForm = Object.assign({}, node)
                this.editFormConfig.title = '修改菜单'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            handleEditFormOpened() {
                if (!this.editFormConfig.isAdd) {
                    var treeNode = this.$refs.updateTree.getNode(this.editForm.parentId)
                    this.editFormConfig.parent = (treeNode && treeNode.data.name) || '--'
                }
            },
            doEdit() {
                let url = '/api/menu'
                let editRes = this.editFormConfig.isAdd ? Tools.http.postJson(url, this.editForm)
                    : Tools.http.putJson(url, this.editForm)
                editRes.then(res => {
                    this.$message.success(this.editFormConfig.title + '成功')
                    this.initMenuTree()
                    this.editFormConfig.visible = false
                })
                
            },
            handleNodeClick(nodeData) {
                this.editFormConfig.parent = nodeData.name
                this.editForm.parentId = nodeData.id
                this.$refs.nodeSelect.blur()
            },
            handleDelete(node) {
                if (node.children && node.children.length > 0) {
                    this.$message.error(node.name + '存在子节点, 无法删除')
                    return
                }
                this.$confirm('确定删除菜单' + node.name, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(_ => Tools.http.delete('/api/menu/' + node.id))
                    .then(res => {
                        this.$message.success('删除菜单成功')
                        this.initMenuTree()
                    })
            },
            handleChangeStatus(row, value) {
                Tools.http.postJson('/api/menu/change/status/' + row.id)
                    .then(res => {
                        this.$message.success('更新状态成功')
                    })
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
                    return (item.name && item.name.indexOf(keyword) !== -1)
                        || (item.remark && item.remark.indexOf(keyword) !== -1)
                }
                return true;
            }
        }
    }
})