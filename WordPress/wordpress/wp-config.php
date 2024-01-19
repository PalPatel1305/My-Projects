<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the installation.
 * You don't have to use the web site, you can copy this file to "wp-config.php"
 * and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * Database settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://wordpress.org/documentation/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** Database settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'wordpress' );

/** Database username */
define( 'DB_USER', 'root' );

/** Database password */
define( 'DB_PASSWORD', '' );

/** Database hostname */
define( 'DB_HOST', 'localhost' );

/** Database charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8mb4' );

/** The database collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication unique keys and salts.
 *
 * Change these to different unique phrases! You can generate these using
 * the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}.
 *
 * You can change these at any point in time to invalidate all existing cookies.
 * This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         '%)a)kQ9:lsh+Fo:I/tga`c_FmdPA61?4]zs?z-<m%&u|u=4*$x#xj9Ojg&b9M.!U' );
define( 'SECURE_AUTH_KEY',  '$2(sI_*]yH!KJqo9p2J#4o[P~@V(RIc.8^z8vBY_q/-pvSw*#3vn8AFO~y14wYNd' );
define( 'LOGGED_IN_KEY',    'm&aXGffidr|bNR:G|OF#pd(+ e~ S=ab4QN26hAoaOaesH3Q;g@JD7m/!r0o`+[w' );
define( 'NONCE_KEY',        '$>v/>+O6}+`.$0tw;iYjqC&t^VkJwIj|hF<QV(s<5$z|Z)}3=-S)W;r/(]-(GlDg' );
define( 'AUTH_SALT',        '.w1A:U<fe(foayC/gns9FAo_cjzQ1_{Wq|50w1x}h+O6F,9=!CAP7eo<oS8j[W69' );
define( 'SECURE_AUTH_SALT', '|1XaMc4#s;)L=9;@-t5K] cCFNg??T4<b)E0+w5[M(Uy-{2`m^!@FmJ^!AtGV6jG' );
define( 'LOGGED_IN_SALT',   'KC=^$R72QDrXh35&*^|(7}0F&0U{bp75Zk-Ei<^7}2jDx}%+zX%3`@CaBN#3|}0b' );
define( 'NONCE_SALT',       '9hpIDn)^N|Zt{5w|sRM8D{Ya$1_qta|{O8/qb}J;Wyza|1q/qZkNr)p`) 0!a8NM' );

/**#@-*/

/**
 * WordPress database table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://wordpress.org/documentation/article/debugging-in-wordpress/
 */
define( 'WP_DEBUG', false );

/* Add any custom values between this line and the "stop editing" line. */



/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
