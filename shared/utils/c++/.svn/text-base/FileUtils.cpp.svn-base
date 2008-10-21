#include "FileUtils.hpp"

#include <sys/statvfs.h>

FileUtils::FileUtils()
{
}

FileUtils::~FileUtils()
{
}

// Determines the available space on disk
int FileUtils::getAvailableDiskSpace (const char *path) 
{    
    struct statvfs *buf = new struct statvfs;
    int fsstatus = statvfs(path, buf);
    if (fsstatus == 0)
        return (buf->f_bavail * buf->f_bsize / 1024);   
    else
        return -1;        
}

