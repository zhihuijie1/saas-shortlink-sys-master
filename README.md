# saas-shortlink-sys-master
短链接saas系统



## git使用说明

github：saas-shortlink-sys-master

本地：shortlink



本地环境下：

```shell
git init

git add . 

git commit -m "1"

git remote add origin git@github.com:zhihuijie1/saas-shortlink-sys-master.git

git branch
// 显示本地是 * master分支，但是github是main分支，需要修改一下分支

git branch -M main

//远程仓库中的 main 分支包含你本地没有的提交。为了解决这个问题，你需要先将远程 main 分支的更改拉取到本地，然后再推送。
git pull origin main --rebase

git push --set-upstream origin main

```

