# user-management-service
- **Dockerhub**: 
  - https://hub.docker.com/r/thanhtung100397/user-management-service/

- **Consul-template**:
  - File template: `haproxy.ctmpl`
  - Khởi động consul-template: <br>
    Ubuntu: `consul-template -template="path/to/haproxy.ctml:path/to/haproxy.cfg:service haproxy reload"`
