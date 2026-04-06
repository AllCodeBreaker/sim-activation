PS D:\Allwin  NM\calde\SimActivationPortal> git push -u origin main
Enumerating objects: 58, done.
Counting objects: 100% (58/58), done.
Delta compression using up to 4 threads
Compressing objects: 100% (48/48), done.
Writing objects: 100% (58/58), 17.57 KiB | 749.00 KiB/s, done.
Total 58 (delta 12), reused 0 (delta 0), pack-reused 0 (from 0)
remote: Resolving deltas: 100% (12/12), done.
remote: error: GH013: Repository rule violations found for refs/heads/main.
remote:
remote: - GITHUB PUSH PROTECTION
remote:   —————————————————————————————————————————
remote:     Resolve the following violations before pushing again
remote:
remote:     - Push cannot contain secrets
remote:
remote:
remote:      (?) Learn how to resolve a blocked push
remote:      https://docs.github.com/code-security/secret-scanning/working-with-secret-scanning-and-push-protection/working-with-push-protection-from-the-command-line#resolving-a-blocked-push   
remote:
remote:
remote:       —— Aiven Service Password ————————————————————————————
remote:        locations:
remote:          - commit: 45ace6797305eb5ad614901e52daa84951d8a9bc
remote:            path: src/main/resources/application.properties:8
remote:          - commit: 45ace6797305eb5ad614901e52daa84951d8a9bc
remote:            path: src/main/resources/application.properties:10
remote:
remote:        (?) To push, remove secret from commit(s) or follow this URL to allow the secret. 
remote:        https://github.com/AllCodeBreaker/sim-activation/security/secret-scanning/unblock-secret/3BymCSEgFC89iE5f85bLbcvlZOk
remote:
remote:
remote:
To https://github.com/AllCodeBreaker/sim-activation.git
 ! [remote rejected] main -> main (push declined due to repository rule violations)
error: failed to push some refs to 'https://github.com/AllCodeBreaker/sim-activation.git'