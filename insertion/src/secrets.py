import secrets

# Generate 256-bit random number
rand_num = secrets.token_hex(32)
print(rand_num)
