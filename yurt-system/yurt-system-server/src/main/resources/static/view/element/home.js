define(function () {
    let tmpl = `
    <main class="main">
                <el-breadcrumb class="breadcrumb">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item>活动管理</el-breadcrumb-item>
                    <el-breadcrumb-item>活动列表</el-breadcrumb-item>
                    <el-breadcrumb-item>活动详情</el-breadcrumb-item>
                </el-breadcrumb>
                <div class="main__bd">
                    <slot></slot>
                </div>
            </main>
`
    return {
        template: tmpl
    }
})