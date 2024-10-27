# Exam Essentials

### Recap / Important Concepts Covered

- We saw how Physical network transformed into virtual network in Cloud
- AWS Region, Availability zone and VPC relation
- VPC Addressing – CIDR
- VPC Components – Subnets, Route Tables, Internet Gateway
- IP Addresses – Private vs Public vs Elastic
- Elastic Network Interfaces
- Firewalls – Security groups and Network ACLs
- NAT – Nat Gateways and NAT Instance

### Exam Essentials

- Maximum size of VPC/Subnet is /16 which contains 65536 IP addresses
- Minimum size of VPC/Subnet is /28 which contains 16 IP addresses
- In every subnet 5 IPs are un-usable – first 4 and last IP in the subnet
- Subnet is associated with AZ. One subnet can not span across AZs. However one AZ may contain any number of subnets.
- All subnets by default follow VPC main route table unless explicitly attached to custom route table
- All route tables have a default local route which you can’t remove.This route enables inter VPC communication between all subnets


### Exam Essentials
- You can’t block any IP with Security group. Use Network ACL.
- Security groups is stateful - No need to explicitly add outbound rule for incoming return traffic or inbound rules for outbound return traffic
- Network ACL is stateless – Rules must be created for both side of the traffic
- NAT Gateway must be created in each AZ for High Availability.
- NAT Gateways must be created in Public Subnet
- NAT Gateway does not have security group hence traffic should be controlled by using Network ACL
- You don’t get access to NAT Gateway machine. It’s managed by AWS.
- NAT Instance may be cost effective but may not be as performant and resilient as NAT Gateway.
- For NAT Instance, Source/Destination check must be disabled









