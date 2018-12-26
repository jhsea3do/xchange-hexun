package si.mazi.rescu.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * Copy From org.glassfish.jersey.server.JSONP *
 *
 * <p>Annotation can be used to define the JavaScript callback function name if the valid JSONP
 * format is requested as an acceptable {@link javax.ws.rs.core.MediaType media type} of this
 * request. At the moment only resource methods are supported to be annotated with this annotation.
 *
 * <p>The acceptable JavaScript media types for JSONP compatible with this annotation are:
 *
 * <ul>
 *   <li>application/x-javascript
 *   <li>application/javascript
 *   <li>application/ecmascript
 *   <li>text/javascript
 *   <li>text/x-javascript
 *   <li>text/ecmascript
 *   <li>text/jscript
 * </ul>
 *
 * <p>Note: Determining the JavaScript callback function name from a query parameter ({@link
 * #queryParam}) takes precedence over the {@link #callback()} value.
 *
 * @author Michal Gajdos
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONP {

  /** Default JavaScript callback function name. */
  public static final String DEFAULT_CALLBACK = "callback";

  /** Default query parameter name to obtain the JavaScript callback function name from. */
  public static final String DEFAULT_QUERY = "__callback";

  /** Name of the JavaScript callback function to which the JSON result should be wrapped into. */
  String callback() default DEFAULT_CALLBACK;

  /**
   * If set then the JavaScript callback function name is obtained from a query parameter with the
   * given name. If this query parameter is not present in the request then the value of {@link
   * #callback()} property is used as the JavaScript callback function name.
   */
  String queryParam() default "";
}
