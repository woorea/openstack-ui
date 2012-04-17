package org.openstack.ui.server;

import java.io.Serializable;
import java.util.List;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.TenantList;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.images.Image;
import org.openstack.model.images.ImageList;
import org.openstack.ui.client.UIService;
import org.openstack.ui.client.common.UIException;

public class UIServiceImpl extends OpenStackRemoteServiceServlet implements UIService {

	@Override
	public ServerList listServers(int start, int max) throws UIException {
		return getComputeClient().listServers();
	}

	@Override
	public Server create(ServerForCreate serverForCreate) throws UIException {
		return getComputeClient().createServer(serverForCreate);
	}

	@Override
	public void deleteServer(String id) throws UIException {
		getComputeClient().deleteServer(id);
		
	}

	@Override
	public void deleteServers(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteServer(id);
		}
	}

	@Override
	public Serializable executeServerAction(String id, ServerAction action) throws UIException {
		return getComputeClient().executeServerAction(id, action);
	}

	@Override
	public FlavorList listFlavors(int start, int max) throws UIException {
		return getComputeClient().listFlavors();
	}

	@Override
	public Flavor create(Flavor flavor) throws UIException {
		//return getComputeClient();
		return null;
	}

	@Override
	public void deleteFlavor(String id) throws UIException {
		getComputeClient().deleteFlavor(id);
		
	}

	@Override
	public void deleteFlavors(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteFlavor(id);
		}
		
	}

	@Override
	public ImageList listImages(int starts, int max) throws UIException {
		return getImagesClient().listImages();
	}

	@Override
	public Image create(Image image) throws UIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImage(String id) throws UIException {
		//getComputeClient().deleteImage(id);
		
	}

	@Override
	public void deleteImages(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteImage(id);
		}
		
	}

	@Override
	public List<FloatingIp> listFloatingIps() throws UIException {
		return getComputeClient().listFloatingIps();
	}

	@Override
	public FloatingIp createFloatingIp() throws UIException {
		return getComputeClient().createFloatingIp(null);
	}

	@Override
	public void deleteFloatingIp(Integer id) throws UIException {
		getComputeClient().deleteFloatingIp(id);
		
	}

	@Override
	public void deleteFloatingIps(Integer[] ids) throws UIException {
		for(Integer id : ids)  {
			deleteFloatingIp(id);
		}
		
	}

	@Override
	public List<Volume> listVolumes() throws UIException {
		return getComputeClient().listVolumes();
	}

	@Override
	public Volume create(VolumeForCreate volumeForCreate) throws UIException {
		return getComputeClient().createVolume(volumeForCreate);
	}

	@Override
	public void deleteVolume(Integer id) throws UIException {
		getComputeClient().deleteVolume(id);
		
	}

	@Override
	public void deleteVolumes(Integer[] ids) throws UIException {
		for(Integer id : ids)  {
			deleteVolume(id);
		}
		
	}

	@Override
	public Volume attachVolume(Integer id, String serverId) throws UIException {
		// serveraction
		return null;
	}

	@Override
	public Volume detachVolume(Integer id) throws UIException {
		// serveraction
		return null;
	}

	@Override
	public List<Snapshot> listSnapshots() throws UIException {
		return getComputeClient().listSnapshots();
	}

	@Override
	public Snapshot create(SnapshotForCreate snapshotForCreate) throws UIException {
		return getComputeClient().createSnapshot(snapshotForCreate);
	}

	@Override
	public void deleteSnapshot(Integer id) throws UIException {
		getComputeClient().deleteSnapshot(id);
		
	}

	@Override
	public void deleteSnapshots(Integer[] ids) throws UIException {
		for(Integer id : ids)  {
			deleteSnapshot(id);
		}
		
	}

	@Override
	public List<KeyPair> listKeyPairs() throws UIException {
		return getComputeClient().listKeyPairs();
	}

	@Override
	public KeyPair createKeyPair(String name) throws UIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteKeyPair(String name) throws UIException {
		getComputeClient().deleteKeyPair(name);
		
	}

	@Override
	public void deleteKeyPairs(String[] names) throws UIException {
		for(String name : names) {
			deleteKeyPair(name);
		}
		
	}

	@Override
	public List<SecurityGroup> listSecurityGroups() throws UIException {
		return getComputeClient().listSecurityGroups();
	}

	@Override
	public SecurityGroup create(SecurityGroupForCreate securityGroup) throws UIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityGroup showSecurityGroup(Integer id) throws UIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSecurityGroup(Integer id) throws UIException {
		getComputeClient().deleteSecurityGroup(id);
		
	}

	@Override
	public void deleteSecurityGroups(Integer[] ids) throws UIException {
		for(Integer id : ids)  {
			deleteSecurityGroup(id);
		}
		
	}

	@Override
	public SecurityGroupRule create(SecurityGroupRuleForCreate rule) throws UIException {
		return getComputeClient().createSecurityGroupRule(rule);
	}

	@Override
	public void deleteSecurityGroupRule(Integer id) throws UIException {
		//getComputeClient().deleteSecurityGroupRule(id);
		
	}

	@Override
	public void deleteSecurityGroupRules(Integer[] ids) throws UIException {
		for(Integer id : ids)  {
			deleteSecurityGroupRule(id);
		}
		
	}

	@Override
	public TenantList listTenants(int start, int max) throws UIException {
		return getIdentityClient().listTenants(start, max);
	}

	@Override
	public Tenant create(Tenant tenant) throws UIException {
		return getIdentityClient().createTenant(tenant);
	}

	@Override
	public void deleteTenant(String id) throws UIException {
		getIdentityClient().deleteTenant(id);
		
	}

	@Override
	public void deleteTenants(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteTenant(id);
		}
		
	}

	@Override
	public List<User> listUsers() throws UIException {
		return getIdentityClient().listUsers();
	}

	@Override
	public User create(UserForCreate userForCreate) throws UIException {
		return getIdentityClient().createUser(userForCreate);
	}

	@Override
	public void deleteUser(String id) throws UIException {
		getIdentityClient().deleteUser(id);
		
	}

	@Override
	public void deleteUsers(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteUser(id);
		}
		
	}

	@Override
	public List<Role> listRoles() throws UIException {
		return getIdentityClient().listRoles();
	}

	@Override
	public Role create(Role role) throws UIException {
		return getIdentityClient().createRole(role);
	}

	@Override
	public void deleteRole(String id) throws UIException {
		getIdentityClient().deleteRole(id);
		
	}

	@Override
	public void deleteRoles(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteRole(id);
		}
		
	}

	@Override
	public List<Service> listServices() throws UIException {
		return getIdentityClient().listServices();
	}

	@Override
	public Service create(Service service) throws UIException {
		return getIdentityClient().createService(service);
	}

	@Override
	public void deleteService(String id) throws UIException {
		getIdentityClient().deleteService(id);
		
	}

	@Override
	public void deleteServices(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteService(id);
		}
		
	}

	@Override
	public List<Endpoint> listEndpoints() throws UIException {
		return getIdentityClient().listEndpoints();
	}

	@Override
	public Endpoint create(Endpoint endpoint) throws UIException {
		return getIdentityClient().createEndpoint(endpoint);
	}

	@Override
	public void deleteEndpoint(String id) throws UIException {
		getIdentityClient().deleteEndpoint(id);
		
	}

	@Override
	public void deleteEndpoints(String[] ids) throws UIException {
		for(String id : ids)  {
			deleteEndpoint(id);
		}
		
	}

}
