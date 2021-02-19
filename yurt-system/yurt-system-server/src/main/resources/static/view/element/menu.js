define(["text!/view/element/menu.html"], function (tmpl) {
    return {
        template: tmpl,
        data: function(){
            return {
                menuTree: [],
                menuList: [],
                editFormConfig: {
                    formLabelWidth: '120px',
                    title: '新增菜单',
                    visible: false
                },
                editForm: {}
            }
        },
        created: function(){
            this.initMenuTree()
        },
        methods: {
            initMenuTree: function() {
                Tools.http.get('/api/menu/tree')
                    .then(res => this.menuTree = [{id: 0, name: '全部菜单', children: res.data}])
            },
            handleNodeClick: function(item, node, component) {
                this.menuList = item.children || []
            }
        }
    }
})