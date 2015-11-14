api_base： /api/v1


用户表------------------------------------------------------
id：         用户id
name：       用户名
nickname：   用户昵称
passworld:   密码

api
/session                            post：登录
/users                              post：注册
/users/<id>                         get：查看用户信息  post： put：修改用户信息
/users/<id>/follows                 post：关注某人



角色-----------------------------------------------------------
id：         角色id：
name：       角色名
img：        角色图片
pirate_tuan：角色所属
descr：      角色介绍


api
/roles                              get：获取所有的role post：添加角色 put：修改角色 delete：删除角色
