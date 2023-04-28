# EFS

## Performance & Storage Classes

**EFS Scale**

• 1000s of concurrent NFS clients, 10 GB+ /s throughput

• Grow to Petabyte-scale network file system, automatically

**Performance mode (set at EFS creation time)**

• General purpose (default): latency-sensitive use cases (web server, CMS, etc…)

• Max I/O – higher latency, throughput, highly parallel (big data, media processing)

**Throughput mode**

• Bursting (1 TB = 50MiB/s + burst of up to 100MiB/s)

• Provisioned: set your throughput regardless of storage size, ex: 1 GiB/s for 1 TB storage