use Crypt::PBE qw(:jce);
use MIME::Base64;
use Getopt::Long;

my $value     = 'password';
my $secret    = 'secret';
my $debug     = undef;
my $operation = 'decrypt';

GetOptions(
    'value=s'     => \$value,
    'debug'       => \$debug,
    'secret=s'    => \$secret,
    'operation=s' => \$operation,

);

# jasypt_encryptor_password
my $password = $secret;
my $pbe      = PBEWithMD5AndDES($password);

# NOTE: can only use the JCE_PBE_ALGORITHMS that are known to Crypt::PBE.pm
# see
# jasypt.encryptor.algorithm = PBEWithMD5AndDES
if ( $operation =~ /encrypt/ ) {
    my $encrypted = $pbe->encrypt( $value, 1000 ); 
    # NOTE: Base64 encrypted data

    $encoded = encode_base64($encrypted);
    print $encoded, $/;
    # NOTE: occasionally produce values like '+i8WOMARx/Jmn4uOFn2yEA=='
    if ($debug) {
        print $pbe->decrypt($encrypted), $/;
    }
} else {
    # TODO: occasionally see
    # input must be 8 bytes long at /usr/local/lib/x86_64-linux-gnu/perl/5.26.1/Crypt/DES.pm line 65
    my $encrypted = decode_base64($value);
    if ($debug) {
        print $value, $/;
    }
    print $pbe->decrypt($encrypted), $/;
}
