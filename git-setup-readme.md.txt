After installing git, first you need to set your name and email
git config --global user.name "T-Sangeeth"
git config --global user.email tsangeeth@gmail.com

create authentication private/public keys
	ssh-keygen
update github with public key

checkout code
git clone git@github.com:tsangeeth/cab-o-meter.git

After making changes....


Adding files
	git add -i

Commit to local repository
	git commit -m"some comment | <your name in angle bracket>"

update before pushing changes to remote
	git pull

Finally push to remote
	git push
