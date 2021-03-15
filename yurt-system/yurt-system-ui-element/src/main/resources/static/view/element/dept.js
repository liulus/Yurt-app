define(["text!/view/element/dept.html"], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                deptData: [],
                showData: [],
                keyword: '',
                rootId: 0,
                dataLoaded: false,
                expandKeys: [],
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    isRoot: false,
                    title: '',
                    parent: ""
                },
                editForm: {}
            }
        },
        created: function () {
            this.initData()
        },
        methods: {
            initData: function () {
                Tools.http.get('/api/dept/tree')
                    .then(res => {
                        this.deptData = res.data
                        this.showData = res.data
                        if (res.data && res.data.length > 0) {
                            this.rootId = res.data[0].id
                            this.expandKeys = [this.rootId + '']
                        }
                        this.dataLoaded = true
                    })
            },
            handleAddRoot() {
                this.editForm = { parentId: 0 }
                this.editFormConfig.title = '新增部门'
                this.editFormConfig.isRoot = true
                this.editFormConfig.visible = true
            },
            handleAdd(parentNode) {
                this.editForm = { parentId: parentNode ? parentNode.id : 0 }
                this.editFormConfig.title = '新增部门'
                this.editFormConfig.parent = parentNode ? parentNode.name : '--'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
            },
            handleEdit(node) {
                this.editForm = Object.assign({}, node)
                this.editFormConfig.title = '修改部门'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            handleEditFormOpened() {
                // 弹框没有完成时 tree 没有加载, ref 引用会为null, 所以在弹框opened实践处理父节点
                if (!this.editFormConfig.isAdd) {
                    var treeNode = this.$refs.updateTree.getNode(this.editForm.parentId)
                    this.editFormConfig.parent = (treeNode && treeNode.data.name) || '--'
                }
            },
            doEdit() {
                let url = '/api/dept'
                let editRes = this.editFormConfig.isAdd ? Tools.http.postJson(url, this.editForm)
                    : Tools.http.putJson(url, this.editForm)
                editRes.then(res => {
                    this.$message.success(this.editFormConfig.title + '成功')
                    this.initData()
                    this.editFormConfig.isRoot = false
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
                this.$confirm('确定删除部门' + node.name, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(_ => Tools.http.delete('/api/dept/' + node.id))
                    .then(res => {
                        this.$message.success('删除部门成功')
                        this.initData()
                    })
            },
            handleChangeStatus(row, value) {
                Tools.http.postJson('/api/dept/change/status/' + row.id)
                    .then(res => {
                        this.$message.success('更新状态成功')
                    })
            },
            handleSearch() {
                this.expandKeys = [this.rootId + '']
                this.showData = this.filterNode(this.keyword, this.deptData)
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