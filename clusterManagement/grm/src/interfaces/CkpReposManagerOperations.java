package interfaces;

/**
 *	Generated from IDL interface "CkpReposManager"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CkpReposManagerOperations
{
	/* constants */
	/* operations  */
	void setCkpReposMode(java.lang.String executionId, int mode, int nNodes, int nExtra);
	subtypes.CkpReposAddressList getCkpRepos(java.lang.String executionId, java.lang.String ipAddress, int ckpNumber, java.lang.String[] checksumList, int ckpSize);
	types.CkpInfo getLastCkpInfo(java.lang.String executionId);
	int registerCkpRepos(java.lang.String ipAddress, short port, int availableSpace);
	void notifyCkpStored(int ckpReposId, java.lang.String executionId, int ckpNumber, int fragmentNumber, int availableSpace);
	int updateRepositoryStatus(int ckpReposId, int availableSpace);
	subtypes.CheckpointRemovalList getCheckpointRemovalList(int ckpReposId);
}
