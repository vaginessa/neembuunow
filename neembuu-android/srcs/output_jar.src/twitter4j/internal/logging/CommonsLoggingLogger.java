package twitter4j.internal.logging;

import org.apache.commons.logging.Log;

final class CommonsLoggingLogger
  extends Logger
{
  private final Log LOGGER;
  
  CommonsLoggingLogger(Log paramLog)
  {
    this.LOGGER = paramLog;
  }
  
  public void debug(String paramString)
  {
    this.LOGGER.debug(paramString);
  }
  
  public void debug(String paramString1, String paramString2)
  {
    this.LOGGER.debug(paramString1 + paramString2);
  }
  
  public void error(String paramString)
  {
    this.LOGGER.error(paramString);
  }
  
  public void error(String paramString, Throwable paramThrowable)
  {
    this.LOGGER.error(paramString, paramThrowable);
  }
  
  public void info(String paramString)
  {
    this.LOGGER.info(paramString);
  }
  
  public void info(String paramString1, String paramString2)
  {
    this.LOGGER.info(paramString1 + paramString2);
  }
  
  public boolean isDebugEnabled()
  {
    return this.LOGGER.isDebugEnabled();
  }
  
  public boolean isErrorEnabled()
  {
    return this.LOGGER.isErrorEnabled();
  }
  
  public boolean isInfoEnabled()
  {
    return this.LOGGER.isInfoEnabled();
  }
  
  public boolean isWarnEnabled()
  {
    return this.LOGGER.isWarnEnabled();
  }
  
  public void warn(String paramString)
  {
    this.LOGGER.warn(paramString);
  }
  
  public void warn(String paramString1, String paramString2)
  {
    this.LOGGER.warn(paramString1 + paramString2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.CommonsLoggingLogger
 * JD-Core Version:    0.7.0.1
 */