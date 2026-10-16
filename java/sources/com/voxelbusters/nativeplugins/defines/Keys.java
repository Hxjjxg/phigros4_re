package com.voxelbusters.nativeplugins.defines;

/* JADX INFO: loaded from: classes.dex */
public class Keys {
    public static final String ATTACHMENT = "attachment";
    public static final String BODY = "body";
    public static final String BUTTON_LIST = "button-list";
    public static final String CALLBACK = "callback";
    public static final String ERROR = "error";
    public static final String EXCLUDE_LIST = "exclude-list";
    public static final String HTML = "html";
    public static final String IMAGE_PATH = "image-path";
    public static final String IS_SECURE = "is-secure";
    public static final String MESSAGE = "message";
    public static final String ORIENTATION = "orientation";
    public static final String PLACE_HOLDER_TEXT_1 = "place-holder-text-1";
    public static final String PLACE_HOLDER_TEXT_2 = "place-holder-text-2";
    public static final String RESULT = "result";
    public static final String SCALE_FACTOR = "scale-factor";
    public static final String SUBJECT = "subject";
    public static final String TAG = "tag";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String URL = "url";

    public class Sharing {
        public static final String BCC_RECIPIENT_LIST = "bcc-recipient-list";
        public static final String CC_RECIPIENT_LIST = "cc-recipient-list";
        public static final String CLOSED = "closed";
        public static final String FAILED = "failed";
        public static final String MAIL = "mail";
        public static final String MIME_TYPE = "mime-type";
        public static final String SMS = "sms";
        public static final String TO_RECIPIENT_LIST = "to-recipient-list";
        public static final String WHATS_APP = "whatsapp";

        public Sharing() {
        }
    }

    public class AddressBook {
        public static final String ACCESS_AUTHORIZED = "authorized";
        public static final String ACCESS_DENIED = "denied";
        public static final String ACCESS_RESTRICTED = "restricted";
        public static final String AUTH_STATUS = "auth-status";
        public static final String CONTACTS_LIST = "contacts-list";
        public static final String DISPLAY_NAME = "display-name";
        public static final String EMAIL_ID_LIST = "email-list";
        public static final String FAMILY_NAME = "family-name";
        public static final String GIVEN_NAME = "given-name";
        public static final String IMAGE_PATH = "image-path";
        public static final String PERMISSION_REQUEST_REASON = "Need this permission to read contacts.";
        public static final String PHONE_NUM_LIST = "phone-number-list";

        public AddressBook() {
        }
    }

    public class Ui {
        public static final String BUTTON_PRESSED = "button-pressed";
        public static final String CALLER = "caller";
        public static final String INPUT = "input";
        public static final String PASSWORD = "password";
        public static final String USER_NAME = "username";

        public Ui() {
        }
    }

    public class Billing {
        public static final String DEVELOPER_PAYLOAD = "developerPayload";
        public static final String ERROR = "error";
        public static final String ORIGINAL_JSON = "original-json";
        public static final String PRODUCTS_LIST = "products-list";
        public static final String PRODUCT_CURRENCY_CODE = "price_currency_code";
        public static final String PRODUCT_CURRENCY_SYMBOL = "price_currency_symbol";
        public static final String PRODUCT_IDENTIFIER = "productId";
        public static final String PURCHASE_VALIDATION_STATE = "purchaseValidationState";
        public static final String RAW_PURCHASE_DATA = "raw-purchase-data";
        public static final String SIGNATURE = "signature";
        public static final String TRANSACTIONS_LIST = "transactions-list";

        public Billing() {
        }

        public class PurchaseState {
            public static final String FAILED = "failed";
            public static final String PURCHASED = "purchased";
            public static final String REFUNDED = "refunded";
            public static final String RESTORED = "restored";

            public PurchaseState() {
            }
        }

        public class Validation {
            public static final String DIDNT_VALIDATE = "no-validation-done";
            public static final String FAILED = "failed";
            public static final String SUCCESS = "success";

            public Validation() {
            }
        }
    }

    public class MediaLibrary {
        public static final String CAMERA_PERMISSION_REASON = "Need camera permission for taking pictures.";
        public static final String CANCEL = "Cancel";
        public static final String CHOOSE_FROM_GALLERY = "Choose from gallery";
        public static final String EMBEDDED_PLAYER_SCHEME = "embeddedplayer";
        public static final String FINISH_REASON = "finish-reason";
        public static final String IMAGE_PATH = "image-path";
        public static final String OPEN_CAMERA = "Open camera";
        public static final String PICK_IMAGE_CANCELLED = "pick-image-cancelled";
        public static final String PICK_IMAGE_FAILED = "pick-image-failed";
        public static final String PICK_IMAGE_SELECTED = "pick-image-selected";
        public static final String PICK_VIDEO_CANCELLED = "pick-video-cancelled";
        public static final String PICK_VIDEO_FAILED = "pick-video-failed";
        public static final String PICK_VIDEO_SELECTED = "pick-video-selected";
        public static final String PLAY_VIDEO_ENDED = "play-video-ended";
        public static final String PLAY_VIDEO_ERROR = "play-video-error";
        public static final String READ_STORAGE_PERMISSION_REASON = "Need read external storage permission for reading images from gallery.";
        public static final String SELECT_MEDIA = "Select media";
        public static final String SELECT_SOURCE = "Select source";
        public static final String USER_EXITED = "user-exited";
        public static final String WRITE_STORAGE_PERMISSION_REASON = "Need write external storage permission for writing images to gallery.";
        public static final String YOUTUBE_VIDEO_ID = "youtube-video-id";

        public MediaLibrary() {
        }
    }

    public class Notification {
        public static final String IS_LAUNCH_NOTIFICATION = "is-launch-notification";
        public static final String IS_REMOTE_NOTIFICATION = "is-remote-notification";
        public static final String NOTIFICATION_PAYLOAD = "notification-data";
        public static final String SAVED_ALLOWED_NOTIFICATION_TYPES = "notification-types-allowed";
        public static final String SAVED_CUSTOM_ICON_SELECTION = "allow-custom-icon-drawing";
        public static final String SAVED_KEYS_FILE = "np-saved-notification-keys-data";
        public static final String SAVED_KEYS_INFO = "keys-info";
        public static final String SAVED_NOTIFICATION_META_INFO = "notification-meta-info";
        public static final String SAVED_VIBRATION_SELECTION = "allow-vibration";
        public static final String USES_EXTERNAL_REMOTE_NOTIFICATION_SERVICE = "uses-extenral-remote-notification-service";

        public Notification() {
        }
    }

    public class WebView {
        public static final String ACCEPTABLE_DATA_MIME = "mime-types";
        public static final String ARGUMENTS = "arguments";
        public static final String FILE_CHOOSER = "FILE_CHOOSER";
        public static final String HOST = "host";
        public static final String MESSAGE_DATA = "message-data";
        public static final String TAG = "tag";
        public static final String TYPE_CLOSE_BUTTON = "CLOSE_OPTION";
        public static final String TYPE_NO_CONTROLS = "NONE";
        public static final String TYPE_TOOLBAR = "TOOLBAR_OPTION";
        public static final String URL_SCHEME = "url-scheme";

        public WebView() {
        }
    }

    public class Twitter {
        public static final String AUTH_TOKEN = "auth-token";
        public static final String AUTH_TOKEN_SECRET = "auth-token-secret";
        public static final String COMPOSER_ACTION_CANCELLED = "cancelled";
        public static final String COMPOSER_ACTION_DONE = "done";
        public static final String IS_PROTECTED = "is-protected";
        public static final String IS_VERIFIED = "is-verified";
        public static final String NAME = "name";
        public static final String PROFILE_IMAGE_URL = "profile-image-url";
        public static final String USER_ID = "user-identifier";
        public static final String USER_NAME = "user-name";

        public Twitter() {
        }
    }

    public class GameServices {
        public static final String ACHIEVED_DESCRIPTION = "achieved-description";
        public static final String ACHIEVEMENTS_LIST = "achievements-list";
        public static final String ACHIEVEMENT_DESCRIPTIONS_LIST = "achievement-descriptions-list";
        public static final String ACHIEVEMENT_ID = "identifier";
        public static final String ACHIEVEMENT_INFO = "achievement-info";
        public static final String ACHIEVEMENT_TITLE = "title";
        public static final String DISPLAY_STRING = "display-string";
        public static final String ERROR = "error";
        public static final String EXTERNAL_CREDENTIALS_DATA = "credentials-data";
        public static final String IMAGE_FILE_PATH = "image-file-path";
        public static final String IMAGE_PATH = "image-path";
        public static final String INSTANCE_ID = "instance-id";
        public static final String IS_COMPLETED = "is-completed";
        public static final String LAST_REPORT_DATE = "last-report-date";
        public static final String LEADERBOARD_ID = "leaderboard-id";
        public static final String LEADERBOARD_INFO = "leaderboard-info";
        public static final String LEADERBOARD_LOCAL_SCORE = "leaderboard-local-score";
        public static final String LEADERBOARD_SCORES_LIST = "leaderboard-scores";
        public static final String LOCAL_USER_FRIENDS = "local-user-friends";
        public static final String LOCAL_USER_INFO = "local-user-info";
        public static final String MAXIMUM_POINTS = "maximum-points";
        public static final String ON_CONNECTION_FAILURE = "on-connection-failure";
        public static final String PENDING_INTENT = "pending-intent";
        public static final String POINTS_SCORED = "points-scored";
        public static final String REQUEST_SERVER_AUTH_CODE = "request-server-auth-code";
        public static final String RESULT_CODE = "result-code";
        public static final String SAVED_AUTO_SIGN_IN_STATUS = "allow-auto-sign-in";
        public static final String SAVED_KEYS_FILE = "np-saved-game-services-keys-data";
        public static final String SCORE_DATE = "date";
        public static final String SCORE_FORMATTED_VALUE = "formatted-value";
        public static final String SCORE_ID = "identifier";
        public static final String SCORE_INFO = "score-info";
        public static final String SCORE_RANK = "rank";
        public static final String SCORE_USER = "user";
        public static final String SCORE_VALUE = "value";
        public static final String SERVER_AUTH_CODE = "server-auth-code";
        public static final String SERVER_CLIENT_ID = "server-client-id";
        public static final String SHOW_ACHIEVEMENTS = "show-achievements";
        public static final String SHOW_LEADERBOARDS = "show-leaderboards";
        public static final String STATE = "state";
        public static final String STATE_HIDDEN = "state-hidden";
        public static final String STATE_REVEALED = "state-revealed";
        public static final String STATE_UNLOCKED = "state-unlocked";
        public static final String TIME_SCOPE_ALL_TIME = "time-scope-all-time";
        public static final String TIME_SCOPE_TODAY = "time-scope-today";
        public static final String TIME_SCOPE_WEEK = "time-scope-week";
        public static final String TIME_SPAN = "time-span";
        public static final String TYPE = "type";
        public static final String TYPE_INCREMENTAL = "type-incremental";
        public static final String TYPE_STANDARD = "type-standard";
        public static final String UNACHIEVED_DESCRIPTION = "un-achieved-description";
        public static final String USERS_LIST = "users-list";
        public static final String USER_ALIAS = "alias";
        public static final String USER_HIGH_RES_IMAGE_URL = "high-res-image-url";
        public static final String USER_ICON_IMAGE_URL = "icon-image-url";
        public static final String USER_ID = "identifier";
        public static final String USER_NAME = "name";
        public static final String USER_SCOPE_FRIENDS = "user-scope-friends";
        public static final String USER_SCOPE_GLOBAL = "user-scope-gobal";
        public static final String USER_TIME_STAMP = "timestamp";

        public GameServices() {
        }
    }

    public class CloudServices {
        public static final String CLOUD_ACCOUNT_NAME = "cloud-account-name";
        public static final String IS_COMMIT_SUCCESS = "is-commit-success";
        public static final String IS_FETCHED_FOR_SAVE = "is-fetched-for-save";
        public static final String NEW_CLOUD_DATA = "new-cloud-data";

        public CloudServices() {
        }
    }

    public class Intent {
        public static final String SCHEME_SEND_TO = "smsto:";
        public static final String SMS_BODY = "sms_body";

        public Intent() {
        }
    }

    public class Mime {
        public static final String EMAIL = "message/rfc822";
        public static final String HTML_TEXT = "text/html";
        public static final String IMAGE_ALL = "image/*";
        public static final String PLAIN_TEXT = "text/plain";
        public static final String VIDEO_ALL = "video/*";

        public Mime() {
        }
    }

    public class Scheme {
        public static final String FILE = "file";
        public static final String HTTP = "http";
        public static final String HTTPS = "https";
        public static final String JAVA_SCRIPT = "javascript";

        public Scheme() {
        }
    }

    public class Package {
        public static final String FACEBOOK_1 = "com.facebook.katana";
        public static final String FACEBOOK_2 = "com.facebook.katana.LoginActivity";
        public static final String GMAIL_1 = "com.google.android.gm";
        public static final String GMAIL_2 = "com.google.android.gm.ConversationListActivity";
        public static final String GOOGLE_PLUS = "com.google.android.apps.plus";
        public static final String INSTAGRAM = "com.instagram.android";
        public static final String TWITTER = "com.twitter.android";
        public static final String WHATS_APP = "com.whatsapp";

        public Package() {
        }
    }

    public class Permission {
        public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
        public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

        public Permission() {
        }
    }
}
