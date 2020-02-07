import org.transmart.searchapp.AuthUser
import org.transmart.searchapp.Requestmap
import org.transmart.searchapp.Role

def getOrCreateDir = { String parent, String path ->
	File dir = new File(parent, path)
	if (!dir.exists()) {
		dir.mkdir()
	}
	dir.absolutePath
}
def homeDir = { String path -> getOrCreateDir(System.getProperty('user.home'), path) }
def tempDir = { String path -> getOrCreateDir(System.getProperty('java.io.tmpdir'), path) }

// Disabling/Enabling UI tabs
// Search was not part of 1.2. It's not working properly. You need to set `show` to `true` to see it on UI
// Note: analyze tab is always shown
// Note: by default the analysisJobs panel is NOT shown
// Currently, it is only used in special cases
ui.tabs.browse.hide = "${System.getenv("HIDE_BROWSE")}".toBoolean()
ui.tabs.datasetExplorer.analysisJobs.show = !("${System.getenv("HIDE_ANALYSIS")}".toBoolean())
ui.tabs.datasetExplorer.dataExport.hide = "${System.getenv("HIDE_EXPORT")}".toBoolean()
ui.tabs.datasetExplorer.dataExportJobs.hide = "${System.getenv("HIDE_EXPORT_JOBS")}".toBoolean()
ui.tabs.datasetExplorer.gridView.hide = "${System.getenv("HIDE_GRID")}".toBoolean()
ui.tabs.datasetExplorer.workspace.hide = "${System.getenv("HIDE_WORKSPACE")}".toBoolean()
ui.tabs.geneSignature.hide = "${System.getenv("HIDE_GENE_SIG")}".toBoolean()
ui.tabs.gwas.hide = "${System.getenv("HIDE_GWAS")}".toBoolean()
ui.tabs.sampleExplorer.hide = "${System.getenv("HIDE_SAMPLE")}".toBoolean()
ui.tabs.search.show = !("${System.getenv("HIDE_SEARCH")}".toBoolean())
ui.tabs.uploadData.hide = "${System.getenv("HIDE_UPLOAD")}".toBoolean()

// appears on the login screen, just below the login button.
// ui.loginScreen.disclaimer = "Please be aware that tranSMART is a data-integration tool that allows for exploration of available study data. The information shown in tranSMART, and derived from performed analyses, are for research purposes only. NOT for decision making in e.g. clinical trial studies."

grails.plugin.springsecurity.apf.storeLastUsername = true
grails.plugin.springsecurity.auth0.active = "${System.getenv("AUTH0_ACTIVE")}".toBoolean()
grails.plugin.springsecurity.auth0.admin.autoCreate = "${System.getenv("AUTH0_ADMIN_CREATE")}".toBoolean() // creates admin user if none exists
grails.plugin.springsecurity.auth0.admin.autoCreateEmail = "${System.getenv("ADMIN_EMAIL")}" // optional
grails.plugin.springsecurity.auth0.admin.autoCreatePassword = "${System.getenv("AUTH0_ADMIN_PASSWORD")}"
grails.plugin.springsecurity.auth0.admin.autoCreateUsername = "${System.getenv("AUTH0_ADMIN_USER")}"
grails.plugin.springsecurity.auth0.clientId = "${System.getenv("CLIENT_ID")}"
grails.plugin.springsecurity.auth0.clientSecret = "${System.getenv("CLIENT_SECRET")}"
grails.plugin.springsecurity.auth0.domain = "${System.getenv("AUTH0_DOMAIN")}"
grails.plugin.springsecurity.auth0.preRegistrationProviderPrefixes = ['oauth2|ORCiD']
grails.plugin.springsecurity.auth0.registrationEnabled = "${System.getenv("AUTH0_REGISTRATION")}".toBoolean() // enable/disable Auth0 user registration
grails.plugin.springsecurity.auth0.useRecaptcha = false
String envUrl = "${System.getenv("AUTH0_WEBTASK_URL")}"
grails.plugin.springsecurity.auth0.webtaskBaseUrl = envUrl == 'null' ? null : envUrl
grails.plugin.springsecurity.authority.className = Role.name
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/**':                          'IS_AUTHENTICATED_REMEMBERED',
	'/accessLog/**':                'ROLE_ADMIN',
	'/analysis/getGenePatternFile': 'permitAll',
	'/analysis/getTestFile':        'permitAll',
	'/assets/**':                   'permitAll',
	'/authUser/**':                 'ROLE_ADMIN',
	'/authUserSecureAccess/**':     'ROLE_ADMIN',
	'/css/**':                      'permitAll',
	'/images/**':                   'permitAll',
	'/js/**':                       'permitAll',
	'/login/**':                    'permitAll',
	'/requestmap/**':               'ROLE_ADMIN',
	'/role/**':                     'ROLE_ADMIN',
	'/search/loadAJAX**':           'permitAll',
	'/secureObject/**':             'ROLE_ADMIN',
	'/secureObjectAccess/**':       'ROLE_ADMIN',
	'/secureObjectPath/**':         'ROLE_ADMIN',
	'/userGroup/**':                'ROLE_ADMIN',
	'/auth0/admin*':                'ROLE_ADMIN',
	'/auth0/**':                    'permitAll',
	'/registration/**':             'permitAll',
	'/cms/**':                      'permitAll'
]
grails.plugin.springsecurity.fii.rejectPublicInvocations = false // revert to old behavior
grails.plugin.springsecurity.logout.afterLogoutUrl = '/'
grails.plugin.springsecurity.rejectIfNoRule = false // revert to old behavior
grails.plugin.springsecurity.requestMap.className = Requestmap.name
// grails.plugin.springsecurity.securityConfigType = 'Requestmap'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/userLanding'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = AuthUser.name
grails.plugin.springsecurity.userLookup.passwordPropertyName = 'passwd'
grails.plugin.springsecurity.userLookup.userDomainClassName = AuthUser.name

// Logging Configuration
grails.logging.jul.usebridge = true
log4j.main = {
	appenders {
		rollingFile name: 'file', maxFileSize: 1024 * 1024, file: 'app.log'
		rollingFile name: 'sql',  maxFileSize: 1024 * 1024, file: 'sql.log'
	}

	error 'org.codehaus.groovy.grails',
	      'org.springframework',
	      'org.hibernate',
	      'net.sf.ehcache.hibernate'
	debug sql: 'org.hibernate.SQL', additivity: false
	debug sql: 'groovy.sql.Sql', additivity: false
	// trace sql: 'org.hibernate.type.descriptor.sql.BasicBinder', additivity: false

	root {
		warn 'file'
	}
}

// Faceted Search Configuration
com.rwg.solr.browse.path = '/solr/browse/select/'
com.rwg.solr.host = 'solr:8983'
com.rwg.solr.path = '/solr/rwg/select/'
com.rwg.solr.scheme = 'http'
com.rwg.solr.update.path = '/solr/browse/dataimport/'
com.recomdata.solr.baseURL = com.rwg.solr.scheme + '://' + com.rwg.solr.host + new File(com.rwg.solr.browse.path).parent
com.recomdata.solr.maxNewsStories = 10
com.recomdata.solr.maxRows = 10000

// Personalization
com.recomdata.adminEmail = "${System.getenv("ADMIN_EMAIL")}" // site administrator contact email address
com.recomdata.contactUs = "${System.getenv("CONTACT_US")}" // contact email address
com.recomdata.defaults.landing = '/datasetExplorer' // default landing page
com.recomdata.largeLogo = 'transmartlogo.jpg' // application logo to be used in the login page
com.recomdata.searchtool.appTitle = 'Department of Biomedical Informatics – tranSMART'
com.recomdata.searchtool.largeLogo = 'transmartlogoHMS.jpg'
com.recomdata.smallLogo = 'transmartlogosmall.jpg' // application logo to be used in the search page

// email content
edu.harvard.transmart.auth0.emailMessage.level1 = "${System.getenv("EMAIL_MESSAGE_LEVEL_1")}"
edu.harvard.transmart.auth0.emailMessage.level2 = "${System.getenv("EMAIL_MESSAGE_LEVEL_2")}"
edu.harvard.transmart.auth0.emailMessage.admin = "${System.getenv("EMAIL_MESSAGE_ADMIN")}"
edu.harvard.transmart.instance.quickstarturl = "${System.getenv("QUICKSTART_URL")}"

edu.harvard.transmart.instance.name = "${System.getenv("I2B2TRANSMART_TITLE")}"
edu.harvard.transmart.instance.type = "${System.getenv("I2B2TRANSMART_INSTANCE_TYPE")}"
edu.harvard.transmart.instance.userguideurl = "${System.getenv("USER_GUIDE_URL")}"
com.recomdata.appTitle = "i2b2/tranSMART " + "${System.getenv("I2B2TRANSMART_TITLE")} v" + "${System.getenv("I2B2TRANSMART_VERSION")}"

// Location of the help pages. Must be an absolute URL. Currently, these are distribution with transmart, so it can also point to that location copy.
com.recomdata.adminHelpURL = 'help/adminHelp/default.htm'

// Keys without defaults (see Config-extra.php.sample):
// name and URL of the supporter entity shown on the welcome page
// com.recomdata.providerName = 'tranSMART Foundation'
// com.recomdata.providerURL = 'http://www.transmartfoundation.org'
// com.recomdata.providerLogo = '/transmart/static/images/transmartlogo.jpg'

// name and URL and logo of the project
// shown on the login page
// com.recomdata.projectName = 'My project'
// com.recomdata.projectURL = 'http://myproject.org/'
// com.recomdata.projectLogo = '/myprojectbanner.jpg'

// Session timeout and heartbeat frequency (ping interval)
com.recomdata.sessionTimeout = 30000
com.recomdata.heartbeatLaps = 300

// Not enabled by default (see Config-extra.php.sample)
//com.recomdata.passwordstrength.pattern
//com.recomdata.passwordstrength.description

// Whether to enable guest auto login. If it's enabled no login is required to access tranSMART.
com.recomdata.guestAutoLogin = "${System.getenv("GUEST_AUTO_LOGIN")}".toBoolean()

// Guest account user name – if guestAutoLogin is true, this is the username of
// the account that tranSMART will automatically authenticate users as. This will
// control the level of access anonymous users will have (the access will be match
// that of the account specified here).
com.recomdata.guestUserName = "${System.getenv("GUEST_USER")}"

// Sample Explorer configuration

// This is an object to dictate the names and 'pretty names' of the SOLR fields.
// Optionally you can set the width of each of the columns when rendered
// TODO: this is configured twice in transmart-data:release-16.2. Is it a bug? -Andre
sampleExplorer.fieldMapping = [columns: [
	[header: 'ID',              dataIndex: 'id',              mainTerm: true,  showInGrid: true, width: 20],
	[header: 'trial name',      dataIndex: 'trial_name',      mainTerm: true,  showInGrid: true, width: 20],
	[header: 'barcode',         dataIndex: 'barcode',         mainTerm: true,  showInGrid: true, width: 20],
	[header: 'plate id',        dataIndex: 'plate_id',        mainTerm: true,  showInGrid: true, width: 20],
	[header: 'patient id',      dataIndex: 'patient_id',      mainTerm: true,  showInGrid: true, width: 20],
	[header: 'external id',     dataIndex: 'external_id',     mainTerm: true,  showInGrid: true, width: 20],
	[header: 'aliquot id',      dataIndex: 'aliquot_id',      mainTerm: true,  showInGrid: true, width: 20],
	[header: 'visit',           dataIndex: 'visit',           mainTerm: true,  showInGrid: true, width: 20],
	[header: 'sample type',     dataIndex: 'sample_type',     mainTerm: true,  showInGrid: true, width: 20],
	[header: 'description',     dataIndex: 'description',     mainTerm: true,  showInGrid: true, width: 20],
	[header: 'comment',         dataIndex: 'comment',         mainTerm: true,  showInGrid: true, width: 20],
	[header: 'location',        dataIndex: 'location',        mainTerm: true,  showInGrid: true, width: 20],
	[header: 'organism',        dataIndex: 'source_organism', mainTerm: true,  showInGrid: true, width: 20],
	// consolidated the sampleExplorer field mapping. is this a bug? -Andre
	[header: 'Sample ID',       dataIndex: 'id',              mainTerm: false, showInGrid: false],
	[header: 'BioBank',         dataIndex: 'BioBank',         mainTerm: true,  showInGrid: true, width: 10],
	[header: 'Source Organism', dataIndex: 'Source_Organism', mainTerm: true,  showInGrid: true, width: 10]
	// Continue as you have fields
]]
sampleExplorer.idfield = 'id'
sampleExplorer.resultsGridHeight = 100
sampleExplorer.resultsGridWidth = 100

// TODO: this is configured twice in transmart-data:release-16.2. Is it a bug? -Andre
edu.harvard.transmart.sampleBreakdownMap = [
	aliquot_id: 'Aliquots in Cohort',
	id: 'Aliquots in Cohort'
]

// Rserve configuration
// we don't need to specify temporaryImageDirectory, because we're not copying
RModules.external = "${System.getenv("RSERVE_EXTERNAL")}".toBoolean()
RModules.host = "${System.getenv("RSERVE_HOST")}"
RModules.imageURL = '/tempImages/' // This is not used in recent versions; the URL is always /analysisFiles/; must end and start with /
RModules.pluginScriptDirectory = "${System.getenv("RSCRIPTS_DIR")}"
RModules.port = 6311
// The working directory for R scripts, where the jobs get created and output files get generated
// This is a remote Rserve directory. Bear in mind the need for shared network storage
RModules.tempFolderDirectory = '/tmp'
com.recomdata.plugins.tempFolderDirectory = RModules.tempFolderDirectory // Used to access R jobs parent directory outside RModules (e.g. data export)
grails.resources.adhoc.excludes = [ '/images' + RModules.imageURL + '**' ] // Grabbed from Miscellaneous Configuration

// GWAS Configuration
com.recomdata.dataUpload.appTitle = 'Upload data to tranSMART'
com.recomdata.dataUpload.etl.dir = homeDir('.grails/transmart-gwasetl') // Directory path of com.recomdata.dataUpload.stageScript
com.recomdata.dataUpload.stageScript = 'run_analysis_stage'
com.recomdata.dataUpload.templates.dir = homeDir('.grails/transmart-templates') // Directory to preload with template files with names <type>-template.txt
com.recomdata.dataUpload.uploads.dir = homeDir('.grails/transmart-datauploads') // Directory to hold GWAS file uploads

// Quartz jobs configuration
com.recomdata.export.jobs.sweep.startDelay = 60 * 1000
com.recomdata.export.jobs.sweep.repeatInterval = 24 * 60 * 60 * 1000
com.recomdata.export.jobs.sweep.fileAge = 3 // the age of files to be deleted (in days)

// File store and indexing configuration
com.recomdata.FmFolderService.filestoreDirectory = homeDir('.grails/transmart-filestore')
com.recomdata.FmFolderService.importDirectory = tempDir('transmart-fileimport')

// Email notification configuration
edu.harvard.transmart.email.logo = '/images/info_security_logo_rgb.png'
edu.harvard.transmart.email.notify = "${System.getenv("NOTIFICATION_EMAILS")}"

grails.mail.host = 'smtp.gmail.com'
grails.mail.password = "${System.getenv("EMAIL_PASS")}"
grails.mail.port = 587
grails.mail.username = "${System.getenv("EMAIL_USER")}"
grails.mail.props = [
	'mail.smtp.auth': 'true',
	'mail.smtp.starttls.enable': 'true',
	'mail.smtp.ssl.enable': 'false',
	'mail.smtp.socketFactory.port': '587',
	'mail.smtp.socketFactory.fallback': 'false']

// gNOME integration
edu.harvard.transmart.gnome.baseUrl = "${System.getenv("GNOME_BASE_URL")}"
edu.harvard.transmart.gnome.enabled = "${System.getenv("GNOME_ENABLED")}".toBoolean()
edu.harvard.transmart.gnome.password = "${System.getenv("GNOME_PASSWORD")}"
edu.harvard.transmart.gnome.username = "${System.getenv("GNOME_USERNAME")}"
String envProjects = "${System.getenv("GNOME_PROJECTS")}"
edu.harvard.transmart.gnome.projects = (!envProjects || envProjects == 'null') ? [] : Eval.me(envProjects)

edu.harvard.transmart.gridview.expandFolderIntoColumns = true // if true, expand a folder dropped onto grid view into individual columns, otherwise retain old behavior of aggregating everything in one column

// list of paths to exclude from grid view display and export
String envBlacklist = "${System.getenv("GRIDVIEW_BLACKLIST_PATHS")}"
edu.harvard.transmart.gridview.blacklist.paths = (!envBlacklist || envBlacklist == 'null') ? [] : Eval.me(envBlacklist)

// Fractalis configuration
fractalis.active = "${System.getenv("FRACTALIS_ACTIVE")}".toBoolean()
fractalis.dataSource = "${System.getenv("FRACTALIS_DATA_SOURCE")}" // Must be a PIC-SURE endpoint unless i2b2-tranSMART supports additional data APIs.
fractalis.resourceName = "${System.getenv("FRACTALIS_RESOURCE_NAME")}" // Must be a resource name that 'fractalis.dataSource' has access to. E.g. '/nhanes/Demo'
fractalis.node = "${System.getenv("FRACTALIS_NODE")}" // Must be a Fractalis endpoint. See https://git-r3lab.uni.lu/Fractalis for further information.

environments {
	development {
		com.recomdata.bugreportURL = "${System.getenv("BUG_REPORT_URL")}"
		com.recomdata.sessionTimeout = Integer.MAX_VALUE / 1000 as int // ~24 days
		com.recomdata.heartbeatLaps = 900
	}
}

org.transmart.configFine = true
