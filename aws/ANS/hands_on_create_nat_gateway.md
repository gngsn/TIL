# Hands On: Create NAT Gateway

- NAT Gateway must be created in Public Subnet so that it can communicate with the Internet
- NAT Gateway should be allocated Elastic IP

## Exercise 3

- Create a NAT Gateway in your VPC

  - VPC => NAT Gateways => Create NAT Gateway
    - Subnet: MyVPC-Public (Must select Public Subnet)
    - EIP: Create New EIP
    - Create NAT Gateway
    - It takes 5-10 minutes for NAT Gateway to be Active

- Add a route in Private subnet for internet traffic and route through NAT Gateway
  - Route Tables => Select MyVPC-Private route table
  - Routes => Edit => Add another route
    - Destination: 0.0.0.0/0
    - Target: nat-gateway
    - Save

- Now again try to ping google.com from EC2-B
  - ping google.com



