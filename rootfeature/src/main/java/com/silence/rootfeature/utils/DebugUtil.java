package com.silence.rootfeature.utils;

import android.content.Intent;
import android.net.Uri;

import com.silence.rootfeature.app.C;
import com.silence.rootfeature.logger.LogManager;

import java.util.List;
import java.util.Set;

/**
 * @author violet
 * date :  2018/4/27 16:24
 */

public class DebugUtil {

    public static final boolean enable = true;
    public static void displayIntentInfo(Intent intent){
        if(enable){
            if(intent != null){
                StringBuilder sb = new StringBuilder("intentInfo-->");
                sb.append(" scheme = ").append(intent.getStringExtra(C.UriInfo.KEY_SCHEME))
                        .append(" host =").append(intent.getStringExtra(C.UriInfo.KEY_HOST))
                        .append(" port = ").append(intent.getIntExtra(C.UriInfo.KEY_PORT,0))
                        .append(" path = ").append(intent.getStringExtra(C.UriInfo.KEY_PATH))
                        .append(" encoded_path =").append(intent.getStringExtra(C.UriInfo.KEY_ENCODED_PATH))
                        .append(" authority = ").append(intent.getStringExtra(C.UriInfo.KEY_AUTHORITY))
                        .append(" encoded_authority = ").append(intent.getStringExtra(C.UriInfo.KEY_ENCODED_AUTHORITY))
                        .append(" query = ").append(intent.getStringExtra(C.UriInfo.KEY_ENCODED_QUERY))
                        .append(" encoded_query = ").append(intent.getStringExtra(C.UriInfo.KEY_ENCODED_QUERY))
                        .append(" fragment = ").append(intent.getStringExtra(C.UriInfo.KEY_FRAGMENT))
                        .append(" encoded_fragment = ").append(intent.getStringExtra(C.UriInfo.KEY_ENCODED_FRAGMENT))
                        .append(" lastPathSegement = ").append(intent.getStringExtra(C.UriInfo.KEY_LAST_PATH_SEGEMENT));
                LogManager.getInstance().d(sb.toString());
            }
        }
    }

    public static void displayUri(Uri uri) {
        if(enable && uri != null){
            String scheme = uri.getScheme();
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String authority = uri.getAuthority();
            String fragment = uri.getFragment();
            String lastPathSegment = uri.getLastPathSegment();
            List<String> pathSegments =  uri.getPathSegments();
            String query = uri.getQuery();
            Set<String> parameterNames = uri.getQueryParameterNames();
            String encodedQuery = uri.getEncodedQuery();

            LogManager.getInstance().d(uri.toString());

            StringBuilder sb = new StringBuilder("uriInfo-->");
            sb.append(" scheme =").append(scheme)
                    .append(" host = ").append(host)
                    .append(" port = ").append(port)
                    .append(" path = ").append(path)
                    .append(" encoded_path = ").append(uri.getEncodedPath())
                    .append(" authority = ").append(authority)
                    .append(" encoded_authority = ").append(uri.getEncodedAuthority())
                    .append(" lastPathSegement = ").append(lastPathSegment)
                    .append(" pathSegements = ").append(pathSegments)
                    .append(" query = ").append(query)
                    .append(" encoded_query = ").append(encodedQuery)
                    .append(" fragment = ").append(fragment);
            LogManager.getInstance().d(sb.toString());
        }
    }
}
