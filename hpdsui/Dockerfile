FROM dbmi/pic-sure-hpds-ui:18.1-Quickstart-v2

COPY server.crt /usr/local/apache2/cert/server.crt
COPY server.chain /usr/local/apache2/cert/server.chain
COPY server.key /usr/local/apache2/cert/server.key

COPY tsmart-overrides.js /usr/local/apache2/htdocs/tsmart-overrides.js
COPY httpd-vhosts.conf /usr/local/apache2/conf/extra/httpd-vhosts.conf
